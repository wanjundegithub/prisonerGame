package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.enums.OptionEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.ScoreMapper;
import com.company.prisoner.game.model.*;
import com.company.prisoner.game.param.GroupParam;
import com.company.prisoner.game.param.OptionParam;
import com.company.prisoner.game.param.ScoreParam;
import com.company.prisoner.game.vo.ScoreVO;
import com.company.prisoner.game.vo.UserGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private GroupService groupService;

    /**
     * 获取当前游戏下的所有分数情况
     * @param scoreParam
     * @return
     */
    public Result<List<ScoreVO>> getAllScores(ScoreParam scoreParam){
        List<ScoreVO> resultList;
        List<Score> allScores = scoreMapper.getScoreList(scoreParam);
        if (CollectionUtils.isEmpty(allScores)) {
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", new ArrayList<>());
        }
        Map<Integer, User> userMap = userService.reGetAllUsers();
        resultList = buildScoreVOList(allScores, userMap);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", resultList);
    }

    /**
     * 获取当前游戏下用户的选择并计算每个用户的分数，最终将他们写入表中
     * @param scoreParam
     */
    @Transactional(rollbackFor = Exception.class)
    public Result saveAllScores(ScoreParam scoreParam){
        Integer gameId = scoreParam.getGameId();
        //1.获取当前游戏下的所有选择option(只有stop状态下才能触发计算保存)
        OptionParam optionParam = new OptionParam();
        optionParam.setGameId(gameId);
        List<Option> optionList = optionService.getAllOptions(optionParam);
        if(CollectionUtils.isEmpty(optionList)){
            log.error("当前没有任何用户提交选择,无法计算分数, scoreParam:{}", JSON.toJSONString(scoreParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前没有任何用户提交选择,无法计算分数");
        }
        //2.获取当前游戏下的所有分组
        GroupParam groupParam = new GroupParam();
        groupParam.setGameId(gameId);
        List<Group> groupList = groupService.getAllGroup(groupParam);
        if(CollectionUtils.isEmpty(groupList)){
            log.error("无有效的分组信息, scoreParam:{}",  JSON.toJSONString(scoreParam));
            return Result.buildResult(ResultEnum.FAILED.getCode(),  "无有效的分组信息");
        }
        Map<Integer, User> userMap = userService.reGetAllUsers();
        if(CollectionUtils.isEmpty(userMap)){
            log.error("用户组基础数据为空，无法查询分组数据, gameId:{}", gameId);
            return Result.buildResult(ResultEnum.FAILED.getCode(), "用户组基础数据为空，无法查询分组数据");
        }
        List<UserGroupVO> userGroupVOList = groupService.splitGroupList(userMap, groupList);
        //3.获取分组下的所有的分数
        List<Score> scoreList = getUserScoreList(optionList, userGroupVOList, groupList);
        scoreMapper.batchInsertScore(scoreList);
        //5.将所有分数写入分数表中
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(),  "");
    }

    /**
     * 根据以分组数作为标准,比较选择数和当时的分组数,是否相等,如果option小于分组数，那么将未做选择的记作零分进行补充
     * 并且按照规则进行计算
     * 根据规则计算：双方合作1，每个人得三分;双方背叛，每个人得一分;一方合作，一方背叛，合作得0分，背叛得5分
     * 有一方未做出选择，未作出选择记0分，另一方合作得1分，背叛得2分
     * @param optionList
     * @param userGroupVOList
     * @param groupList
     */
    private List<Score> getUserScoreList(List<Option> optionList, List<UserGroupVO> userGroupVOList,
                                  List<Group> groupList){
        List<Score> allScoreList = new ArrayList<>(userGroupVOList.size()+1);
        Map<Integer, UserGroupVO> allUserGroupMap = userGroupVOList.stream()
                .collect(Collectors.toMap(UserGroupVO::getUserId, Function.identity()));
        Map<Integer, Option> optionMap = optionList.stream()
                .collect(Collectors.toMap(Option::getUserId, Function.identity()));
        List<Score> unChooseScoreList = new ArrayList<>(userGroupVOList.size()+1);
        //先计算出对应的未作选择的用户分数
        for(UserGroupVO userGroupVO: userGroupVOList){
            Integer userId = userGroupVO.getUserId();
            if(!optionMap.containsKey(userId)){
                Score score = Score.build(0, userGroupVO);
                unChooseScoreList.add(score);
            }
        }
        allScoreList.addAll(unChooseScoreList);

        //判断出未作选择的用户同一组的做了选择用户的分数
        List<Score> remainChooseList = new ArrayList<>();

        //分组中全部做出了选择的用户分数集合
        List<Score> pairChooseList = new ArrayList<>(optionList.size());
        for(Group group: groupList){
            Integer userIdFirst = group.getUserIdFirst();
            Integer userIdSecond = group.getUserIdSecond();
            if(!optionMap.containsKey(userIdFirst)&&optionMap.containsKey(userIdSecond)){
                //未作选择的用户同一组的做了选择用户的分数
                int scoreValue = computeScore(optionMap.get(userIdSecond));
                Score score = Score.build(scoreValue, allUserGroupMap.get(userIdSecond),
                        optionMap.get(userIdSecond));
                remainChooseList.add(score);
                continue;
            }
            if(optionMap.containsKey(userIdFirst)&&!optionMap.containsKey(userIdSecond)){
                //未作选择的用户同一组的做了选择用户的分数
                int scoreValue = computeScore(optionMap.get(userIdFirst));
                Score score = Score.build(scoreValue, allUserGroupMap.get(userIdFirst),
                        optionMap.get(userIdFirst));
                remainChooseList.add(score);
                continue;
            }
            if(optionMap.containsKey(userIdFirst) && optionMap.containsKey(userIdSecond)){
                //剩余两两配对全部做出选择的用户的分数
                Map<Integer, Integer> pairScoreMap = computeScore(optionMap.get(userIdFirst),
                        optionMap.get(userIdSecond));
                Score scoreFirst = Score.build(pairScoreMap.get(userIdFirst), allUserGroupMap.get(userIdFirst),
                        optionMap.get(userIdFirst));
                Score scoreSecond = Score.build(pairScoreMap.get(userIdSecond), allUserGroupMap.get(userIdSecond),
                        optionMap.get(userIdSecond));
                pairChooseList.add(scoreFirst);
                pairChooseList.add(scoreSecond);
            }
        }
        allScoreList.addAll(remainChooseList);
        allScoreList.addAll(pairChooseList);
        return allScoreList;
    }

    /**
     * 计算单个用户做出了选择，而同一组的用户未做选择的分数
     * 有一方未做出选择，未作出选择记0分，另一方合作得1分，背叛得2分
     * @param option
     * @return
     */
    private int computeScore(Option option){
        int score = -1;
        switch (option.getSelectOption()){
            //合作
            case 1:
                score = 1;
                break;
            //背叛
            case 2:
                score = 2;
                break;
            default:
                log.error("非法选择:"+JSON.toJSONString(option));
                throw new RuntimeException("非法选择:"+JSON.toJSONString(option));

        }
        return score;
    }

    /**
     * 双方都做出了选择
     * 根据规则计算：双方合作1，每个人得三分;双方背叛，每个人得一分;一方合作，一方背叛，合作得0分，背叛得5分
     * @param option1
     * @param option2
     * @return
     */
    private Map<Integer, Integer> computeScore(Option option1, Option option2){
        Map<Integer, Integer> map = new HashMap<>(2);
        if(option1.getSelectOption().equals(OptionEnum.COOPERATION.getCode())
            &&option2.getSelectOption().equals(OptionEnum.COOPERATION.getCode())){
            map.put(option1.getUserId(), 3);
            map.put(option2.getUserId(), 3);
            return map;
        }
        if(option1.getSelectOption().equals(OptionEnum.BETRAY.getCode())
                &&option2.getSelectOption().equals(OptionEnum.BETRAY.getCode())){
            map.put(option1.getUserId(), 1);
            map.put(option2.getUserId(), 1);
            return map;
        }
        if(option1.getSelectOption().equals(OptionEnum.COOPERATION.getCode())
                &&option2.getSelectOption().equals(OptionEnum.BETRAY.getCode())){
            map.put(option1.getUserId(), 1);
            map.put(option2.getUserId(), 5);
            return map;
        }
        if(option1.getSelectOption().equals(OptionEnum.BETRAY.getCode())
                &&option2.getSelectOption().equals(OptionEnum.COOPERATION.getCode())){
            map.put(option1.getUserId(), 5);
            map.put(option2.getUserId(), 1);
            return map;
        }
        log.error("不存在的两两配对的选择,option1:{}, option2:{}", JSON.toJSONString(option1),
                JSON.toJSONString(option2));
        throw new RuntimeException("不存在的两两配对的选择");
    }

    /**
     * 获取分数表下所有的游戏id
     * @return
     */
    public Result<List<Integer>> getAllGameIdList(){
        ScoreParam scoreParam = new ScoreParam();
        List<Integer> gameIdList = scoreMapper.getAllGameIdList(scoreParam);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", gameIdList);
    }

    /**
     * 获取分数表下所有班级名称
     * @return
     */
    public Result<List<String>> getAllClassNameList(ScoreParam scoreParam){
        List<String> classNameList = scoreMapper.getAllClassNameList(scoreParam);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", classNameList);
    }

    /**
     * 分页查询分数列表
     * @param scoreParam
     * @return
     */
    public Result<PageResult<Score>> getPageScoreList(ScoreParam scoreParam){
        Integer total = scoreMapper.getScoreCount(scoreParam);
        if(total==0){
            log.error("当前游戏不存在分数信息, scoreParam:{}", JSON.toJSONString(scoreParam));
            PageResult<Score> pageResult = PageResult.buildPageResult(new ArrayList<>(),
                    0,scoreParam.getPageSize(), scoreParam.getPage());
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", pageResult);
        }
        // 计算偏移量
        Integer offset = (scoreParam.getPage() - 1) * scoreParam.getPageSize();
        scoreParam.setOffset(offset);
        List<Score> scoreList = scoreMapper.getScoreList(scoreParam);
        Map<Integer, User> userMap = userService.reGetAllUsers();
        List<ScoreVO> resultList = buildScoreVOList(scoreList, userMap);
        PageResult<ScoreVO> pageResult = PageResult.buildPageResult(resultList,
                total,scoreParam.getPageSize(), scoreParam.getPage());
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", pageResult);
    }

    private List<ScoreVO> buildScoreVOList(List<Score> scoreList, Map<Integer, User> userMap){
        return scoreList.stream().map(score -> {
            ScoreVO target = new ScoreVO();
            BeanUtils.copyProperties(score, target);
            Integer userId = score.getUserId();
            User user = userMap.get(userId);
            if(user==null){
                throw new RuntimeException("用户不存在");
            }
            target.setNickName(user.getNickName());
            target.setUserName(user.getUserName());
            target.setClassName(user.getClassName());
            return target;
        }).collect(Collectors.toList());
    }

}
