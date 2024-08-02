package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.GameActiveEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.GameMapper;
import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.model.Group;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.param.ScoreParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * @author user
 */
@Service
@Slf4j
public class GameService {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private GroupService groupService;

    /**
     * 创建游戏后马上进行分组
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result createGame(){
        Game game = new Game();
        game.setCreateBy(GameConstants.ADMIN);
        game.setLastUpdateBy(GameConstants.ADMIN);
        game.setAliveFlag(GameActiveEnum.INITIAL.getStatus());
        Integer result = gameMapper.insertGame(game);
        if(result<=0){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "创建游戏失败,请稍后重试");
        }
        return groupService.startGroup(game.getGameId());
    }

    /**
     * 开始游戏
     * @return
     */
    public synchronized Result startGame(Game game){
        game.setAliveFlag(GameActiveEnum.START.getStatus());
        Integer result = gameMapper.updateGame(game);
        if(result>0){
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
        }
        return Result.buildResult(ResultEnum.FAILED.getCode(), "开始游戏失败,请稍后重试");
    }

    /**
     * 结束游戏,并立即统计分数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result stopGame(Game game){
        game.setAliveFlag(GameActiveEnum.STOP.getStatus());
        Integer result = gameMapper.updateGame(game);
        if(result<=0){
            log.error("游戏停止失败, game:{}", JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "游戏停止失败");
        }
        ScoreParam scoreParam = new ScoreParam();
        scoreParam.setGameId(game.getGameId());
        return scoreService.saveAllScores(scoreParam);
    }

    /**
     * 获取已处于完成状态的游戏 取最近时间的
     * @return
     */
    public Result<Game> getLatestFinishedGame(){
        List<Game> gameList = gameMapper.getGame(GameActiveEnum.STOP.getStatus(), null);
        if(CollectionUtils.isEmpty(gameList)){
            Result.buildResult(ResultEnum.FAILED.getCode(),"不存在已经完成的游戏");
        }
        Game latestGame = gameList.stream().max(Comparator.comparing(Game::getLastUpdateDate))
                .orElse(null);
        if(latestGame==null){
            throw new RuntimeException("游戏不存在");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(),"", latestGame);
    }

    /**
     * 获取启动状态下的游戏
     * @return
     */
    public Result<Game> getStartGame(){
        List<Game> gameList = gameMapper.getGame(GameActiveEnum.START.getStatus(), null);
        if(CollectionUtils.isEmpty(gameList)){
            return Result.buildResult(ResultEnum.FAILED.getCode(),"不存在已经启动的游戏");
        }
        if(gameList.size()>1){
            return Result.buildResult(ResultEnum.FAILED.getCode(),"启动的游戏存在多个");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(),"", gameList.get(0));
    }

    public boolean checkStartGame(Integer gameId){
        List<Game> gameList = gameMapper.getGame(GameActiveEnum.START.getStatus(), gameId);
        return !CollectionUtils.isEmpty(gameList) && gameList.size() <= 1;
    }

}
