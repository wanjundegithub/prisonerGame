package com.company.prisoner.game.utils;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class UserUtil {

    /**
     * key表示用户id，value表示用户信息
     */
    private static Map<Integer, User> userOnLineMap = new ConcurrentHashMap<>();

    /**
     * 表示当前在线人数
     */
    private static AtomicInteger onlineCount = new AtomicInteger();

    /**
     * 内存中维护用户id-user的键值对(在容器启动时进行缓存所有数据)
     */
    private static Map<Integer, User> user = new HashMap<>();

    public Map<Integer, User> getUserOnLineMap() {
        return userOnLineMap;
    }

    public void setUserOnLineMap(Map<Integer, User> userOnLineMap) {
        userOnLineMap = userOnLineMap;
    }

    public AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(AtomicInteger onlineCount) {
        onlineCount = onlineCount;
    }

    public Map<Integer, User> getUser() {
        return user;
    }

    public void setUser(Map<Integer, User> user) {
        user = user;
    }

    public static Result login(User user){
        if(userOnLineMap.containsKey(user.getId())){
            log.error("已经处于登陆状态无法再次登陆");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "已经处于登陆状态无法再次登陆");
        }
        userOnLineMap.put(user.getId(), user);
        onlineCount.incrementAndGet();
        log.info("当前在线人数:{}", onlineCount.get());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
    }

    public static Result logout(User user){
        if(!userOnLineMap.containsKey(user.getId())){
            log.error("已经处于登出状态无法再次登出");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "已经处于登出状态无法再次登出");
        }
        userOnLineMap.remove(user.getId());
        onlineCount.decrementAndGet();
        log.info("当前在线人数:{}", onlineCount.get());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
    }

}
