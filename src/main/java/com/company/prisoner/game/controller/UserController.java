package com.company.prisoner.game.controller;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.enums.UserRoleEnum;
import com.company.prisoner.game.model.PageResult;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.UserParam;
import com.company.prisoner.game.service.UserService;
import com.company.prisoner.game.vo.UserDistributionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author user
 */
@RestController
@RequestMapping("/prisoner/game/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserParam userParam){
        if(StringUtils.isEmpty(userParam.getUserName()) || StringUtils.isEmpty(userParam.getPassword())){
            throw new RuntimeException("userName or password is empty");
        }
        return userService.login(userParam);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody UserParam userParam){
        if(StringUtils.isEmpty(userParam.getId())){
            throw new RuntimeException("id is empty");
        }
        return userService.logout(userParam);
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody UserParam userParam){
        if(userParam.getId() ==null
                || (StringUtils.isEmpty(userParam.getUserName())&&StringUtils.isEmpty(userParam.getPassword())
                &&StringUtils.isEmpty(userParam.getNickName())&&StringUtils.isEmpty(userParam.getClassName()))){
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "用户更新个人信息时请检查用户名,密码,昵称,班级是否全部为空");
        }
        return userService.updateUser(userParam);
    }

    @PostMapping("/getUser")
    public Result<User> getUser(@RequestBody UserParam userParam){
        if(StringUtils.isEmpty(userParam.getUserName()) || StringUtils.isEmpty(userParam.getPassword())){
            throw new RuntimeException("userName or password is empty");
        }
        return userService.getUserResult(userParam);
    }

    @PostMapping("/getUserDistribution")
    public Result<UserDistributionVO> getUserDistribution(@RequestBody GameParam param){
        return userService.getUserDistribution(param);
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody User user){
        if(user.getId() ==null){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户删除时id缺失");
        }
        return userService.deleteUser(user);
    }

    @PostMapping("/saveUser")
    public Result saveUser(@RequestBody User user){
        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())
         || StringUtils.isEmpty(user.getNickName()) || StringUtils.isEmpty(user.getRole())
         || StringUtils.isEmpty(user.getClassName())){
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "请检查用户名,密码,昵称,角色是否为空");
        }
        if(UserRoleEnum.validateUserRole(user.getRole())){
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "请检查角色输入是否为错误，只能是admin,normal其中之一");
        }
        return userService.saveUser(user);
    }

    @PostMapping("/saveUserList")
    public Result saveUserList(@RequestBody List<User> userList){
        if(CollectionUtils.isEmpty(userList)){
            throw new RuntimeException("新增用户数据全部为空");
        }
        for(User user: userList){
            if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())
                    || StringUtils.isEmpty(user.getNickName()) || StringUtils.isEmpty(user.getRole())
                    || StringUtils.isEmpty(user.getClassName())){
                return Result.buildResult(ResultEnum.FAILED.getCode(),
                        "请检查用户名,密码,昵称,角色是否为空");
            }
            if(!UserRoleEnum.validateUserRole(user.getRole())){
                return Result.buildResult(ResultEnum.FAILED.getCode(),
                        "请检查角色输入是否为错误，只能是admin,normal其中之一");
            }
        }
        return userService.saveUserList(userList);
    }

    @PostMapping("/getPageUserList")
    public Result<PageResult<User>> getPageUserList(@RequestBody UserParam userParam){
        if(userParam.getPage()==null || userParam.getPageSize()==null){
            log.error("分页数据不存在, userParam:{}", JSON.toJSONString(userParam));
            throw new RuntimeException("分页数据不存在");
        }
        return userService.getPageUserList(userParam);
    }

    @PostMapping("/getAllPageUserList")
    public Result<PageResult<User>> getAllPageUserList(@RequestBody UserParam userParam){
        if(userParam.getPage()==null || userParam.getPageSize()==null){
            log.error("分页数据不存在, userParam:{}", JSON.toJSONString(userParam));
            throw new RuntimeException("分页数据不存在");
        }
        return userService.getAllPageUserList(userParam);
    }

    @PostMapping("/batchDeleteUserList")
    public Result batchDeleteUserList(@RequestBody UserParam userParam){
        if(userParam.getId()==null || CollectionUtils.isEmpty(userParam.getUserList())){
            log.error("当前操作员id为空或待删除用户列表不存在, userParam:{}", JSON.toJSONString(userParam));
            throw new RuntimeException("当前操作员id为空或待删除用户列表不存在");
        }
        List<Integer> userIdList = userParam.getUserList().stream().map(User::getId)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(userIdList)){
            log.error("待删除用户列表ID不存在, userParam:{}", JSON.toJSONString(userParam));
            throw new RuntimeException("待删除用户列表ID不存在");
        }
        return userService.batchDeleteUserList(userParam);
    }

    @PostMapping("/batchUpdateUserList")
    public Result batchUpdateUserList(@RequestBody UserParam userParam){
        if(userParam.getId()==null || CollectionUtils.isEmpty(userParam.getUserList())){
            log.error("当前操作员id为空或待更新用户列表不存在, userParam:{}", JSON.toJSONString(userParam));
            throw new RuntimeException("当前操作员id为空或待更新用户列表不存在");
        }
        for(User user: userParam.getUserList()){
            if(user.getId() ==null
                    || (StringUtils.isEmpty(user.getUserName())&&StringUtils.isEmpty(user.getPassword())
                    &&StringUtils.isEmpty(user.getNickName())&&StringUtils.isEmpty(user.getClassName()))){
                throw new RuntimeException("更新个人信息时请检查id,用户名,密码,昵称,班级是否全部为空");
            }
        }
        return userService.batchUpdateUserList(userParam);
    }

}
