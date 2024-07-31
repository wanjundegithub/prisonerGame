package com.company.prisoner.game.controller;

import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.service.UserService;
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
    public Result login(@RequestBody User user){
        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())){
            throw new RuntimeException("userName or password is empty");
        }
        return userService.login(user);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody User user){
        if(StringUtils.isEmpty(user.getId())){
            throw new RuntimeException("id is empty");
        }
        return userService.logout(user);
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody User user){
        if(user.getId()==null){
            throw new RuntimeException("id is empty");
        }
        userService.updateUser(user);
    }

}
