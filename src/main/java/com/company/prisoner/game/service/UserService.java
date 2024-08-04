package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.UserMapper;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.UserParam;
import com.company.prisoner.game.utils.UserUtil;
import com.company.prisoner.game.vo.UserDistributionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        //在数据库中查询出对应的用户
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
        user.setPassword("********");
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "",user);
    }

    public List<User> selectAllUsers(){
        return userMapper.getAllUsers();
    }

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
        List<User> userList = userMap.values().stream()
                .filter(t-> !t.getId().equals(userId)&&t.getUserName().equals(newUserName))
                .collect(Collectors.toList());
        if(userList.size()>=1){
            log.error("用户更新失败,已存在相同用户名,param:{}", JSON.toJSONString(userParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户更新失败,已存在相同用户名");
        }
        oldUser.setUserName(userParam.getUserName());
        oldUser.setPassword(userParam.getPassword());
        oldUser.setNickName(userParam.getNickName());
        oldUser.setLastUpdateBy(userParam.getUserName());
        int rows = -1;
        synchronized (this){
            rows = userMapper.updateUser(oldUser);
            //同步更新缓存
            UserUtil.update(userId, oldUser);
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
     * 删除用户
     * @param user
     * @return
     */
    public synchronized Result deleteUser(User user){
        int rows = userMapper.deleteUserById(user);
        if(rows<=0){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "删除用户失败,请检查数据库配置");
        }
        //删除用户缓存
        UserUtil.delete(user.getId());
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
        if(UserUtil.save(user.getId(), user)){
            deleteUser(user);
            return Result.buildResult(ResultEnum.FAILED.getCode(), "保存用户失败");
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

}
