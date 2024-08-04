package com.company.prisoner.game.utils;

import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author user
 */
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
     * 表示各个游戏的提交人数 键为游戏id,值为游戏
     */
    private static Map<Integer, Integer> submitCountMap = new ConcurrentHashMap<>();

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

    public static Map<Integer, Integer> getSubmitCountMap() {
        return submitCountMap;
    }

    public static void setSubmitCountMap(Map<Integer, Integer> sourceSubmitCountMap) {
        submitCountMap = sourceSubmitCountMap;
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

    /**
     * 表示当前提交了一个选择
     * @param gameId
     */
    public static void submit(Integer gameId, User user){
        if(submitCountMap.containsKey(gameId)){
            submitCountMap.put(gameId, submitCountMap.get(gameId) + 1);
        }else{
            submitCountMap.put(gameId, 1);
        }
        log.info("当前用户user:{}做出了选择, 当前游戏提交人数为:{}",user.getUserName(), submitCountMap.get(gameId));
    }

    public static void update(Integer userId, User user){
        log.info("当前用户user:{}更新个人信息和在线用户缓存",user.getId());
        if(!userMap.containsKey(userId)){
            log.info("当前用户user:{}不存在,无法更新基础用户缓存",user.getId());
            return;
        }
        //更新基础用户缓存和在线用户缓存
        userMap.put(userId, user);
        if(!userOnLineMap.containsKey(userId)){
            log.info("当前用户user:{}不存在在线用户缓存中,无法更新缓存",user.getId());
            return;
        }
        userOnLineMap.put(userId, user);
    }

    /**
     * 将用户从缓存中删除
     * @param userId
     */
    public static void delete(Integer userId){
        log.info("当前用户user:{}被删除个人信息和在线用户缓存",userId);
        if(!userMap.containsKey(userId)){
            log.info("当前用户user:{}不存在,无法删除用户",userId);
            return;
        }
        userMap.remove(userId);
        if(!userOnLineMap.containsKey(userId)){
            log.info("当前用户user:{}不存在在线用户缓存中,无法删除用户",userId);
            return;
        }
        userOnLineMap.remove(userId);
    }

    public static boolean save(Integer userId, User user){
        log.info("当前用户user:{}增加个人信息缓存",userId);
        if(userMap.containsKey(userId)){
            log.info("当前用户user:{}已经存在,无法新增用户",userId);
            return false;
        }
        userMap.put(userId, user);
        return true;
    }

    public static boolean saveList(List<User> userList){
        log.info("批量用户增加");
        for(User user: userList){
            Integer userId = user.getId();
            if(userMap.containsKey(userId)){
                log.info("当前用户user:{}已经存在,无法新增用户",userId);
                return false;
            }
            userMap.put(userId, user);
        }
        return true;
    }

}
