package com.company.prisoner.game.service;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.UserMapper;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author user
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public synchronized Result login(User user){
        //在数据库中查询出对应的用户
        User currentUser = selectUser(user.getUserName(), user.getPassword());
        if(currentUser==null){
            log.error("用户名或者密码不正确,登陆失败");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户名或者密码不正确,登陆失败");
        }
        return UserUtil.login(currentUser);
    }

    public synchronized Result logout(User user){
        User currentUser = userMapper.selectUserById(user.getId());
        if(currentUser==null){
            log.error("用户不存在");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户不存在");
        }
        return UserUtil.logout(currentUser);
    }

    public User selectUser(String userName, String password){
        return userMapper.selectUser(userName, password);
    }

    public synchronized void updateUser(User user){
        userMapper.updateUser(user);
    }
}
