package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.enums.OptionEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.OptionMapper;
import com.company.prisoner.game.model.Group;
import com.company.prisoner.game.model.Option;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.GroupParam;
import com.company.prisoner.game.param.OptionParam;
import com.company.prisoner.game.utils.UserUtil;
import com.company.prisoner.game.vo.UserGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author user
 */
@Service
@Slf4j
public class OptionService {

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GroupService groupService;

    /**
     * 用户提交自己的选择
     * @return
     */
    public synchronized Result submitOption(OptionParam optionParam){
        Integer userId = optionParam.getUserId();
        Map<Integer, User> userMap = userService.reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空");
        }
        User user = userMap.get(userId);
        if(user==null){
            log.error("当前用户不存在,无法提交选择, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户不存在,无法提交选择");
        }
        //查询game是否处于启动状态 只有处于启动状态下的才能做出选择
        Integer gameId = optionParam.getGameId();
        boolean gameFlag = gameService.checkStartGame(gameId);
        if(!gameFlag){
            log.error("不存在或存在多个启动状态的游戏,无法提交选择, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "不存在或存在多个启动状态的游戏,无法提交选择");
        }
        //查询当前game下的分组是否存在
        Integer groupId = optionParam.getGroupId();
        GroupParam groupParam = new GroupParam();
        groupParam.setGameId(gameId);
        groupParam.setId(groupId);
        List<Group> curGameGroupList = groupService.getAllGroup(groupParam);
        if(CollectionUtils.isEmpty(curGameGroupList)||curGameGroupList.size()>1){
            log.error("当前游戏下不存在分组或存在多个相同分组,无法提交选择, " +
                    "optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "" +
                    "当前游戏下不存在分组或存在多个相同分组,无法提交选择");
        }
        Group curGroup = curGameGroupList.get(0);
        //判断用户id是否在分组中
        if(!curGroup.getUserIdFirst().equals(userId)&&!curGroup.getUserIdSecond().equals(userId)){
            log.error("当前游戏下当前用户不在所属分组,无法提交选择, " +
                    "optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "" +
                    "当前游戏下当前用户不在所属分组,无法提交选择");
        }
        Option option = new Option();
        BeanUtils.copyProperties(optionParam, option);
        int selectOption = OptionEnum.getCodeByDesc(optionParam.getSelectOptionValue());
        if(selectOption<0){
            log.error("当前用户提交了无效选择, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户提交了无效选择");
        }
        option.setSelectOption(selectOption);
        option.setCreateBy(user.getNickName());
        option.setLastUpdateBy(user.getNickName());
        int rows = optionMapper.insertOption(option);
        if(rows<=0){
            log.error("当前用户提交失败, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户提交失败");
        }
        UserUtil.submit(gameId, user);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 查询当前游戏下的所有选择
     * @param optionParam
     * @return
     */
    public List<Option> getAllOptions(OptionParam optionParam){
        Integer gameId = optionParam.getGameId();
        return optionMapper.getOptionList(optionParam);
    }
}
