package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.OperationEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.enums.UserRoleEnum;
import com.company.prisoner.game.mapper.UserMapper;
import com.company.prisoner.game.model.PageResult;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.Score;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.UserParam;
import com.company.prisoner.game.utils.UserUtil;
import com.company.prisoner.game.vo.ScoreVO;
import com.company.prisoner.game.vo.UserDistributionVO;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jdk.jfr.StackTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author user
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public synchronized Result login(UserParam userParam){
        //在数据库中查询出对应的用户 改为从缓存中查询数据
        List<User> userList = selectUserList(userParam);
        if(CollectionUtils.isEmpty(userList)){
            log.error("用户名或者密码不正确, userParam:{}", JSON.toJSONString(userParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户名或者密码不正确");
        }
        if(userList.size()>1){
            log.error("存在多个相同用户名，请检查用户组配置,userParam:{}", JSON.toJSONString(userParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "存在多个相同用户名，请检查用户组配置");
        }
        User currentUser = userList.get(0);
        return UserUtil.login(currentUser);
    }

    public synchronized Result logout(UserParam userParam){
        //改为直接从缓存中查数据
        User currentUser = userMapper.selectUserById(userParam.getId());
        if(currentUser==null){
            log.error("用户不存在");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户不存在");
        }
        return UserUtil.logout(currentUser);
    }

    public List<User> selectUserList(UserParam userParam){
        return userMapper.selectUserList(userParam);
    }

    /**
     * 获取唯一用户
     * @param userParam
     * @return
     */
    public Result<User> getUserResult(UserParam userParam){
        List<User> userList = selectUserList(userParam);
        if(CollectionUtils.isEmpty(userList)||userList.size()>1){
            log.error("不存在或存在多个相同用户");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "不存在或存在多个相同用户");
        }
        User user = userList.get(0);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "",user);
    }

    public List<User> selectAllUsers(){
        return userMapper.getAllUsers();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateUser(UserParam userParam){
        //先查询是否存在或者相同用户名
        Map<Integer, User> userMap= reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户基础数据丢失");
        }
        Integer userId = userParam.getId();
        User oldUser = userMap.get(userId);
        if(oldUser==null){
            log.error("用户不存在,无法更新, param:{}", JSON.toJSONString(userParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户不存在,无法更新");
        }
        String newUserName = userParam.getUserName();
        if(!StringUtils.isEmpty(newUserName)){
            List<User> userList = userMap.values().stream()
                    .filter(t-> !t.getId().equals(userId)&&t.getUserName().equals(newUserName))
                    .collect(Collectors.toList());
            if(userList.size()>=1){
                log.error("用户更新失败,已存在相同用户名,param:{}", JSON.toJSONString(userParam));
                return Result.buildResult(ResultEnum.FAILED.getCode(), "用户更新失败,已存在相同用户名");
            }
        }
        if(!StringUtils.isEmpty(userParam.getUserName())){
            oldUser.setUserName(userParam.getUserName());
        }
        if(!StringUtils.isEmpty(userParam.getPassword())){
            oldUser.setPassword(userParam.getPassword());
        }
        if(!StringUtils.isEmpty(userParam.getNickName())){
            oldUser.setNickName(userParam.getNickName());
        }
        if(!StringUtils.isEmpty(userParam.getClassName())){
            oldUser.setClassName(userParam.getClassName());
        }
        oldUser.setLastUpdateBy(userParam.getUserName());
        int rows = -1;
        synchronized (this){
            rows = userMapper.updateUser(oldUser);
            //同步更新缓存
            if(!UserUtil.update(userId, oldUser)){
                throw new RuntimeException("更新个人信息异常");
            }
        }
        if(rows<=0){
            log.error("用户更新失败,请检查数据库配置, param:{}", JSON.toJSONString(oldUser));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户更新失败,请检查数据库配置");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 优先从缓存中获取数据，如果无法获取数据，则从数据库中再次查询获取
     * @return
     */
    public Map<Integer, User> reGetAllUsers(){
        Map<Integer, User> userMap = UserUtil.getUserMap();
        if(!CollectionUtils.isEmpty(userMap)){
            return userMap;
        }
        log.error("用户组缓存基础数据为空,将重新从数据库中查询获取");
        List<User> userList = selectAllUsers();
        if(CollectionUtils.isEmpty(userList)){
            log.error("用户基础数据为空");
            return new ConcurrentHashMap<>(0);
        }
        userMap = userList.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        UserUtil.setUserMap(userMap);
        log.info("重新缓存并获取的用户数据总数:{}", userMap.size());
        return userMap;
    }

    /**
     * 获取总人数和当前在线人数
     * @return
     */
    public Result<UserDistributionVO> getUserDistribution(GameParam param){
        //获取用户总数
        Map<Integer, User> userMap = reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户基础数据为空");
            return Result.buildResult(ResultEnum.FAILED.getCode(),"用户基础数据为空");
        }
        Integer total = userMap.size();
        //获取在线用户人数
        Integer onlineCount = UserUtil.getOnlineCount().get();
        UserDistributionVO userDistributionVO = new UserDistributionVO();
        userDistributionVO.setTotal(total);
        userDistributionVO.setOnlineCount(onlineCount);
        if(param.getGameId()==null){
            userDistributionVO.setSubmitCount(0);
        }else{
            Integer gameId = param.getGameId();
            Map<Integer, Integer> gameMap = UserUtil.getSubmitCountMap();
            userDistributionVO.setSubmitCount(gameMap.getOrDefault(gameId, 0));
        }
        log.info("当前提交人数/在线人数/总人数: {}/{}/{}",
                userDistributionVO.getSubmitCount(),
                userDistributionVO.getOnlineCount(),
                userDistributionVO.getTotal());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", userDistributionVO);
    }

    /**
     * 删除用户, 在线状态无法进行删除
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result deleteUser(User user){
        int rows = userMapper.deleteUserById(user);
        if(rows<=0){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "删除用户失败,请检查数据库配置");
        }
        Map<Integer, User> onlineUserMap = UserUtil.getUserOnLineMap();
        if(onlineUserMap.containsKey(user.getId())){
            throw new RuntimeException("用户在线状态无法进行删除");
        }
        if(!UserUtil.delete(user.getId())){
            throw new RuntimeException("用户不在用户组缓存中无法删除");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result saveUser(User user){
        //查询用户是否已经存在 存在的无法新增
        String userName = user.getUserName();
        Map<Integer, User> userMap = reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空");
        }
        List<User> userList = new ArrayList<>(userMap.values());
        userList = userList.stream().filter(t->t.getUserName().equals(userName)).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(userList)){
            log.error("已存在相同用户");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "已存在相同用户");
        }
        user.setCreateBy(GameConstants.ADMIN);
        user.setLastUpdateBy(GameConstants.ADMIN);
        int rows = userMapper.insertUser(user);
        if(rows<0){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "保存用户操作失败");
        }
        //更新缓存
        if(!UserUtil.save(user.getId(), user)){
            throw new RuntimeException("保存用户失败");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 保存用户列表
     * @param userList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result saveUserList(List<User> userList){
        Map<Integer, User> userMap = reGetAllUsers();
        List<User> baseUserList = new ArrayList<>(userMap.values());
        if(CollectionUtils.isEmpty(baseUserList)){
            log.error("用户组基础数据为空");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空");
        }
        Set<String> userNameSet = userList.stream().map(User::getUserName).collect(Collectors.toSet());
        if(userNameSet.size()<userList.size()){
            log.error("新增用户组存在相同用户名");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "新增用户组存在相同用户名");
        }
        for(User user: userList){
            String userName = user.getUserName();
            List<User> checkUserList = baseUserList.stream().filter(t->t.getUserName().equals(userName))
                    .collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(checkUserList)){
                log.error("已存在相同用户");
                return Result.buildResult(ResultEnum.FAILED.getCode(), "已存在相同用户");
            }
        }
        userList = userList.stream().map(source -> {
            User target = new User();
            BeanUtils.copyProperties(source, target);
            target.setCreateBy(GameConstants.ADMIN);
            target.setLastUpdateBy(GameConstants.ADMIN);
            return target;
        }).collect(Collectors.toList());
        //插入用户表
        int rows = userMapper.insertUsers(userList);
        if(rows<0){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "保存用户操作失败");
        }
        List<String> userNameList = userList.stream().map(User::getUserName).collect(Collectors.toList());
        UserParam userParam = new UserParam();
        userParam.setUserNameList(userNameList);
        List<User> newUserList = userMapper.selectUserList(userParam);
        //更新缓存
        if(!UserUtil.saveList(newUserList)){
            //删除全部插入的用户列表
            for(User user: newUserList){
                deleteUser(user);
            }
            return Result.buildResult(ResultEnum.FAILED.getCode(), "保存用户失败");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    public Result<PageResult<User>> getPageUserList(UserParam userParam){
        int total = userMapper.selectTotal(userParam);
        if(total==0){
            log.error("用户组不存在, scoreParam:{}", JSON.toJSONString(userParam));
            PageResult<Score> pageResult = PageResult.buildPageResult(new ArrayList<>(),
                    0,userParam.getPageSize(), userParam.getPage());
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组不存在", pageResult);
        }
        // 计算偏移量
        Integer offset = (userParam.getPage() - 1) * userParam.getPageSize();
        userParam.setOffset(offset);
        List<User> userList = userMapper.selectUserList(userParam);
        PageResult<User> pageResult = PageResult.buildPageResult(userList,
                total,userParam.getPageSize(), userParam.getPage());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", pageResult);
    }

    public Result<PageResult<User>> getAllPageUserList(UserParam userParam) {
        int page = userParam.getPage();
        int pageSize = userParam.getPageSize();
        userParam.setPage(null);
        userParam.setPageSize(null);
        List<User> userList = userMapper.selectUserList(userParam);
        if(CollectionUtils.isEmpty(userList)){
            log.error("用户组不存在, scoreParam:{}", JSON.toJSONString(userParam));
            PageResult<Score> pageResult = PageResult.buildPageResult(new ArrayList<>(),
                    0,userParam.getPageSize(), userParam.getPage());
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组不存在", pageResult);
        }
        PageResult<User> pageResult = PageResult.buildPageResult(userList,
                userList.size(), pageSize, page);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", pageResult);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result batchDeleteUserList(UserParam userParam){
        Result validateResult = checkCanOperation(userParam, OperationEnum.DELETE.getDesc());
        if(ResultEnum.FAILED.getCode().equals(validateResult.getCode())){
            return validateResult;
        }
        //删除用户同时需要删除对应缓存
        List<User> userList = userParam.getUserList();
        List<Integer> idList = userList.stream().map(User::getId).collect(Collectors.toList());
        int rows = userMapper.deleteUserList(idList);
        if(rows<=0){
            log.error("批量删除用户操作失败,请检查数据库配置");
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "批量删除用户操作失败,请检查数据库配置");
        }
        //删除对应用户缓存失败,抛出异常
        if(!UserUtil.deleteUserList(userList)){
            throw new RuntimeException("删除用户缓存失败");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    @Transactional(rollbackFor = Exception.class)
    public Result batchUpdateUserList(UserParam userParam){
        Result validateResult = checkCanOperation(userParam, OperationEnum.UPDATE.getDesc());
        if(ResultEnum.FAILED.getCode().equals(validateResult.getCode())){
            return validateResult;
        }
        List<User> userList = userParam.getUserList();
        int rows = userMapper.batchUpdateUserList(userList);
        if(rows<=0){
            log.error("批量更新用户操作失败,请检查数据库配置");
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "批量更新用户操作失败,请检查数据库配置");
        }
        //更新对应用户缓存,抛出异常
        if(!UserUtil.updateUserList(userList)){
            throw new RuntimeException("更新用户缓存失败");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    public Result checkCanOperation(UserParam userParam, String operationDesc){
        Integer userId = userParam.getId();
        //若当前操作用户处于列表中,无法操作自己
        Map<Integer, User> userMap = reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空,无法{}数据", operationDesc);
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "用户组基础数据为空,无法"+operationDesc+"数据");
        }
        User user = userMap.get(userId);
        if(user==null){
            log.error("当前操作用户:{}不在用户组中,无法{}其他用户", user.getUserName(), operationDesc);
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "当前操作用户不在用户组中,无法"+operationDesc+"其他用户");
        }
        log.info("当前用户:{}尝试批量{}其他用户列表", user.getUserName(), operationDesc);
        //仅有管理员能够操作其他人员
        if(!UserRoleEnum.ADMIN.getRole().equals(user.getRole())){
            log.error("当前操作用户:{}不是管理员,无法{}其他用户", user.getUserName(), operationDesc);
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "当前操作用户不是管理员,无法"+operationDesc+"其他用户");
        }
        //要考虑待删除的用户是否在线状态,在线状态无法进行删除但是可以更新
        if(OperationEnum.DELETE.getDesc().equals(operationDesc)){
            Map<Integer, User> onlineUserMap = UserUtil.getUserOnLineMap();
            List<User> userList = userParam.getUserList();
            for(User deleteUser: userList){
                if(onlineUserMap.containsKey(deleteUser.getId())){
                    log.error("待{}用户:{}处于在线状态,无法进行删除", operationDesc,user.getUserName());
                    return Result.buildResult(ResultEnum.FAILED.getCode(),
                            "待"+operationDesc+"用户:"+user.getUserName()+"处于在线状态,无法进行删除");
                }
            }
        }
        List<User> userList = userParam.getUserList();
        userList.forEach(t->t.setLastUpdateBy(user.getUserName()));
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

}
