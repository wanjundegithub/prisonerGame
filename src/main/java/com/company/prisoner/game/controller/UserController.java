package com.company.prisoner.game.controller;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.UserParam;
import com.company.prisoner.game.service.UserService;
import com.company.prisoner.game.vo.UserDistributionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author user
 */
@RestController
@RequestMapping("/prisoner/game/user")
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
        if(userParam.getId() ==null || StringUtils.isEmpty(userParam.getUserName())){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户更新时关键信息缺失");
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
         || StringUtils.isEmpty(user.getNickName()) || StringUtils.isEmpty(user.getRole())){
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "请检查用户名,密码,昵称,角色是否为空");
        }
        return userService.saveUser(user);
    }

    @PostMapping("/saveUserList")
    public Result saveUserList(@RequestBody List<User> userList){
        if(CollectionUtils.isEmpty(userList)){
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "新增用户数据全部为空");
        }
        for(User user: userList){
            if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())
                    || StringUtils.isEmpty(user.getNickName()) || StringUtils.isEmpty(user.getRole())){
                return Result.buildResult(ResultEnum.FAILED.getCode(),
                        "请检查用户名,密码,昵称,角色是否为空");
            }
        }
        return userService.saveUserList(userList);
    }

}
