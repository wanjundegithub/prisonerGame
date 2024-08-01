package com.company.prisoner.game.config;

import com.company.prisoner.game.model.User;
import com.company.prisoner.game.service.UserService;
import com.company.prisoner.game.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserConfig {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initUserCache(){
        List<User> userList = userService.selectAllUsers();
        if(CollectionUtils.isEmpty(userList)){
            log.error("用户基础数据为空");
            return;
        }
        Map<Integer, User> userMap = userList.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        UserUtil.setUserMap(userMap);
        log.info("当前初始化的用户数据总数:{}", userMap.size());
    }
}
