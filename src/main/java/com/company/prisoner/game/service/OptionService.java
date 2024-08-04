package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.enums.GameActiveEnum;
import com.company.prisoner.game.enums.OptionEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.OptionMapper;
import com.company.prisoner.game.model.*;
import com.company.prisoner.game.param.GameParam;
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
    public Result submitOption(OptionParam optionParam){
        //从game列表中搜索有没有处于唯一启动状态的游戏
        Result<Game> gameResult = gameService.getStartGame();
        if(ResultEnum.FAILED.getCode().equals(gameResult.getCode())){
            log.error("{}, optionParam:{}", gameResult.getMessage(), JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), gameResult.getMessage());
        }
        Game game = gameResult.getData();
        Integer gameId = game.getGameId();
        optionParam.setGameId(gameId);
        //查询当前游戏当前用户是否已经提交了选择
        OptionParam queryParam = new OptionParam();
        queryParam.setGameId(gameId);
        queryParam.setUserId(optionParam.getUserId());
        List<Option> optionList = optionMapper.getOptionList(queryParam);
        if(!CollectionUtils.isEmpty(optionList)){
            log.error("用户已经提交了选择,无法再次提交, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户已经提交了选择,无法再次提交");
        }
        Integer userId = optionParam.getUserId();
        Map<Integer, User> userMap = userService.reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空,请检查用户组配置, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空,请检查用户组配置");
        }
        User user = userMap.get(userId);
        if(user==null){
            log.error("当前用户不存在,无法提交选择, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户不存在,无法提交选择");
        }
        //查询当前gameId,userId下的对应的group
        GroupParam groupParam = new GroupParam();
        groupParam.setGameId(gameId);
        groupParam.setUserId(userId);
        List<Group> curGameGroupList = groupService.getAllGroup(groupParam);
        if(CollectionUtils.isEmpty(curGameGroupList)||curGameGroupList.size()>1){
            log.error("当前游戏下对应用户不存在分组或存在多个相同分组,无法提交选择, " +
                    "optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "" +
                    "当前游戏下对应用户不存在分组或存在多个相同分组,无法提交选择");
        }
        Integer groupId = curGameGroupList.get(0).getId();
        Option option = new Option();
        option.setGameId(gameId);
        option.setGroupId(groupId);
        option.setUserId(userId);
        int selectOption = OptionEnum.getCodeByDesc(optionParam.getSelectOptionValue());
        if(selectOption<0){
            log.error("当前用户提交了无效选择, optionParam:{}", JSON.toJSONString(optionParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户提交了无效选择");
        }
        option.setSelectOption(selectOption);
        option.setCreateBy(user.getNickName());
        option.setLastUpdateBy(user.getNickName());
        synchronized(this) {
            int rows = optionMapper.insertOption(option);
            if(rows<=0){
                log.error("当前用户提交失败, optionParam:{}", JSON.toJSONString(optionParam));
                return Result.buildResult(ResultEnum.FAILED.getCode(), "当前用户提交失败");
            }
            UserUtil.submit(gameId, user);
        }
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
