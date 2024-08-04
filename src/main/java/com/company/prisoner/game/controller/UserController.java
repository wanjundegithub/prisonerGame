package com.company.prisoner.game.controller;

import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.UserParam;
import com.company.prisoner.game.service.UserService;
import com.company.prisoner.game.vo.UserDistributionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void updateUser(@RequestBody User user){
        if(user.getId()==null){
            throw new RuntimeException("id is empty");
        }
        userService.updateUser(user);
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
}
