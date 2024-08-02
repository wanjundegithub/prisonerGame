package com.company.prisoner.game.controller;

import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.param.GroupParam;
import com.company.prisoner.game.service.GroupService;
import com.company.prisoner.game.vo.UserGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prisoner/game/group")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/startGroup")
    public Result startGroup(@RequestBody GroupParam groupParam){
        if(groupParam.getGameId()==null){
            log.error("不存在有效的游戏id,无法进行分组");
            throw new RuntimeException("不存在有效的游戏id,无法进行分组");
        }
        return groupService.startGroup(groupParam.getGameId());
    }

    @PostMapping("/getAllGroups")
    public Result<List<UserGroupVO>> getAllGroups(@RequestBody GroupParam groupParam){
        if(groupParam.getGameId()==null){
            log.error("不存在有效的游戏id,无法获取所有的分组数据");
            throw new RuntimeException("不存在有效的游戏id,无法获取所有的分组数据");
        }
        return groupService.selectUserGroupList(groupParam);
    }
}
