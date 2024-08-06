package com.company.prisoner.game.service;

import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.GroupMapper;
import com.company.prisoner.game.model.Group;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GroupParam;
import com.company.prisoner.game.utils.UserUtil;
import com.company.prisoner.game.vo.UserGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserService userService;

    private static final int MIN_USER_COUNT = 2;

    /**
     * 开始创建分组
     * @param gameId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result<User> startGroup(Integer gameId){
        Map<Integer, User> userOnLineMap = UserUtil.getUserOnLineMap();

        List<User> userList = new ArrayList<>(userOnLineMap.values());
        User unGroupUser = new User();
        //分组剔除管理员参与
        userList = userList.stream().filter(t->!GameConstants.ADMIN.equals(t.getRole()))
                .collect(Collectors.toList());
        if(userList.size()<MIN_USER_COUNT){
            throw new RuntimeException("在线人数少于2人无法进行分组,当前在线人数"+userOnLineMap.size());
        }
        List<Group> resultGroups = generateGroups(userList, unGroupUser);
        //将当前分组结果写入数据库
        List<Group> finalResultGroups = resultGroups;
        resultGroups = IntStream.range(0, resultGroups.size())
                .mapToObj(index -> {
                    Group source = finalResultGroups.get(index);
                    source.setGameId(gameId);
                    source.setGroupSequence(index + 1); // 设置groupSequence为索引值加1
                    source.setCreateBy(GameConstants.ADMIN);
                    source.setLastUpdateBy(GameConstants.ADMIN);
                    return source;
                })
                .collect(Collectors.toList());
        groupMapper.insertGroupList(resultGroups);
        if(unGroupUser.getId()!=null){
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", unGroupUser);
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 分组规则：取当前在线的人员进行分组(需要人数限定，人数少于2人无法进行游戏)
     * 首先取第一个，然后从剩下的第二个到第N个中随机取一个，依次类推，直到所有分组完成
     * 如果最后剩余一个，剩余的一个将进行剔除，无法进行分组
     * @param userList
     * @param unGroupUser
     * @return
     */
    public List<Group> generateGroups(List<User> userList, User unGroupUser) {
        List<Group> groupList = new ArrayList<>();
        List<User> remainingUsers = new ArrayList<>(userList);

        while (remainingUsers.size() > 1) {
            int firstIndex = 0;
            int secondIndex = -1;

            if (remainingUsers.size() > 2) {
                secondIndex = new Random().nextInt(remainingUsers.size() - 1) + 1;
            }else {
                secondIndex = 1;
            }

            User user1 = remainingUsers.get(firstIndex);
            User user2 = remainingUsers.get(secondIndex);

            Group group = new Group();
            group.setUserIdFirst(user1.getId());
            group.setUserIdSecond(user2.getId());

            groupList.add(group);

            remainingUsers.remove(user1);
            remainingUsers.remove(user2);
        }

        if (remainingUsers.size() == 1) {
            User.build(remainingUsers.get(0), unGroupUser);
        }

        return groupList;
    }

    /**
     * 查询某一局游戏下的所有分组(包含对应用户的信息)
     * @param param
     * @return
     */
    public Result<List<UserGroupVO>> selectUserGroupList(GroupParam param){
        Integer gameId = param.getGameId();
        //从全局缓存中获取到所有用户信息组
        Map<Integer, User> userMap = userService.reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空，无法查询分组数据, gameId:{}", gameId);
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空，无法查询分组数据");
        }
        //查询游戏下对应的分组数据
        List<Group> groupList = groupMapper.selectGroupList(param);
        if(CollectionUtils.isEmpty(groupList)){
            log.error("当前游戏下分组数据为空, gameId:{}", gameId);
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前游戏下分组数据为空");
        }
        //将数据组装返回
        List<UserGroupVO> resultUserGroupVOList= splitGroupList(userMap, groupList);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", resultUserGroupVOList);
    }

    public List<UserGroupVO> splitGroupList(Map<Integer, User> userMap, List<Group> groupList){
        List<UserGroupVO> resultUserGroup = new ArrayList<>(groupList.size() * 2+1);
        for(Group group: groupList){

            Integer userIdFirst = group.getUserIdFirst();
            User userFirst = userMap.get(userIdFirst);
            if(userFirst==null){
                log.error("无法在缓存中查询到对应用户的基础信息,userId:{}", userIdFirst);
                throw new RuntimeException("无法在缓存中查询到对应用户的基础信息");
            }
            UserGroupVO itemFirst = UserGroupVO.buildUserGroupVO(group, userFirst);
            resultUserGroup.add(itemFirst);

            Integer userIdSecond = group.getUserIdSecond();
            User userSecond = userMap.get(userIdSecond);
            if(userSecond==null){
                log.error("无法在缓存中查询到对应用户的基础信息,userId:{}", userIdSecond);
                throw new RuntimeException("无法在缓存中查询到对应用户的基础信息");
            }
            UserGroupVO itemSecond = UserGroupVO.buildUserGroupVO(group, userSecond);
            resultUserGroup.add(itemSecond);
        }
        return resultUserGroup;
    }

    /**
     * 获取当前游戏下的所有分组
     * @param
     * @return
     */
    public List<Group> getAllGroup(GroupParam param){
        return groupMapper.selectGroupList(param);
    }

}
