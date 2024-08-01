package com.company.prisoner.game.service;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.UserMapper;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<User> selectAllUsers(){
        return userMapper.getAllUsers();
    }

    public synchronized void updateUser(User user){
        userMapper.updateUser(user);
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
            return new ConcurrentHashMap<>();
        }
        userMap = userList.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        UserUtil.setUserMap(userMap);
        log.info("重新缓存并获取的用户数据总数:{}", userMap.size());
        return userMap;
    }
}
