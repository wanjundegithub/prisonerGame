package com.company.prisoner.game.utils;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import lombok.extern.slf4j.Slf4j;

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
     * 内存中维护用户id-user的键值对(在容器启动时进行缓存所有数据) 在修改/新增/删除的时候需要同步修改这个数据
     */
    private static Map<Integer, User> userMap = new ConcurrentHashMap<>();

    public static Map<Integer, User> getUserOnLineMap() {
        return userOnLineMap;
    }

    public static void setUserOnLineMap(Map<Integer, User> sourceUserOnLineMap) {
        userOnLineMap = new ConcurrentHashMap<>(sourceUserOnLineMap);
    }

    public static AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public static void setOnlineCount(AtomicInteger sourceOnlineCount) {
        onlineCount = sourceOnlineCount;
    }

    public static Map<Integer, User> getUserMap() {
        return userMap;
    }

    public static void setUserMap(Map<Integer, User> sourceUserMap) {
        userMap = new ConcurrentHashMap<>(sourceUserMap);
    }

    public static Result login(User user){
        if(userOnLineMap.containsKey(user.getId())){
            log.error("已经处于登陆状态无法再次登陆");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "已经处于登陆状态无法再次登陆");
        }
        userOnLineMap.put(user.getId(), user);
        onlineCount.incrementAndGet();
        log.info("当前登录人员学号:{}, 当前在线人数:{}", user.getUserName(),onlineCount.get());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
    }

    public static Result logout(User user){
        if(!userOnLineMap.containsKey(user.getId())){
            log.error("已经处于登出状态无法再次登出");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "已经处于登出状态无法再次登出");
        }
        userOnLineMap.remove(user.getId());
        onlineCount.decrementAndGet();
        log.info("当前退出登录用户学号:{}, 当前在线人数:{}", user.getUserName(),onlineCount.get());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
    }

}
