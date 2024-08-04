package com.company.prisoner.game.service;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.GameActiveEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.GameMapper;
import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.param.ScoreParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public synchronized Result<Game> createGame(){
        //需要先检测是否有初始或初始状态下的游戏,如果存在,那么无法创建游戏
        GameParam param = new GameParam();
        List<Game> gameList = gameMapper.getGameList(param);
        gameList = gameList.stream().filter(t-> GameActiveEnum.INITIAL.getStatus()==t.getAliveFlag()
        ||GameActiveEnum.START.getStatus()==t.getAliveFlag()).collect(Collectors.toList());
        if(gameList.size()>0){
            log.error("存在已创建或已开启的游戏, 无法重复创建游戏");
            return Result.buildResult(ResultEnum.FAILED.getCode(), "存在已创建或已开启的游戏," +
                    "无法重复创建游戏");
        }
        Game game = new Game();
        game.setCreateBy(GameConstants.ADMIN);
        game.setLastUpdateBy(GameConstants.ADMIN);
        game.setAliveFlag(GameActiveEnum.INITIAL.getStatus());
        Integer result = gameMapper.insertGame(game);
        if(result<=0){
            log.error("创建游戏失败,请稍后重试, game:{}", JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "创建游戏失败,请稍后重试");
        }
        Result groupResult = groupService.startGroup(game.getGameId());
        if(ResultEnum.FAILED.getCode().equals(groupResult.getCode())){
            //当前游戏更新成无效状态
            game.setAliveFlag(GameActiveEnum.INVALID.getStatus());
            gameMapper.updateGame(game);
            log.error("分组创建失败无法创建游戏,请稍后重试, game:{}", JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "分组创建失败无法创建游戏,请稍后重试");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", game);
    }

    /**
     * 开始游戏
     * @return
     */
    public synchronized Result startGame(GameParam gameParam){
        //先要查询对应游戏是否存在 且处于创建状态才能启动
        Result<Game> gameResult = getGame(gameParam);
        if(ResultEnum.FAILED.getCode().equals(gameResult.getCode())){
            return gameResult;
        }
        Game game =gameResult.getData();
        if(GameActiveEnum.INITIAL.getStatus()!=game.getAliveFlag()){
            return Result.buildResult(ResultEnum.FAILED.getCode(), "当前游戏不处于创建状态,无法启动游戏");
        }
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
    public synchronized Result stopGame(GameParam gameParam){
        //检测是否处于启动状态或创建状态
        Result<Game> gameResult = getGame(gameParam);
        if(ResultEnum.FAILED.getCode().equals(gameResult.getCode())){
            return gameResult;
        }
        Game game =gameResult.getData();
        if(GameActiveEnum.STOP.getStatus()== game.getAliveFlag()){
            log.error("游戏处于停止状态无法再次停止, game:{}", JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "游戏处于停止状态无法再次停止");
        }
        ScoreParam scoreParam = new ScoreParam();
        scoreParam.setGameId(game.getGameId());
        Result scoreResult =  scoreService.saveAllScores(scoreParam);
        if(ResultEnum.FAILED.getCode().equals(scoreResult.getCode())){
            log.error("{}, game:{}", scoreResult.getMessage(), JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), scoreResult.getMessage());
        }
        game.setAliveFlag(GameActiveEnum.STOP.getStatus());
        Integer result = gameMapper.updateGame(game);
        if(result<=0){
            log.error("游戏停止失败, game:{}", JSON.toJSONString(game));
            return Result.buildResult(ResultEnum.FAILED.getCode(), "游戏停止失败");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "");
    }

    /**
     * 获取已处于完成状态的游戏 取最近时间的
     * @return
     */
    public Result<Game> getLatestFinishedGame(){
        GameParam param = new GameParam();
        param.setAliveFlag(GameActiveEnum.STOP.getStatus());
        List<Game> gameList = gameMapper.getGameList(param);
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
        GameParam param = new GameParam();
        param.setGameId(GameActiveEnum.START.getStatus());
        List<Game> gameList = gameMapper.getGameList(param);
        if(CollectionUtils.isEmpty(gameList)){
            return Result.buildResult(ResultEnum.FAILED.getCode(),"不存在已经启动的游戏");
        }
        if(gameList.size()>1){
            return Result.buildResult(ResultEnum.FAILED.getCode(),"启动的游戏存在多个");
        }
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(),"", gameList.get(0));
    }


    /**
     * 获取唯一游戏，要求有gameId
     * @param param
     * @return
     */
    public Result<Game> getGame(GameParam param){
        List<Game> gameList = gameMapper.getGameList(param);
        if(CollectionUtils.isEmpty(gameList)||gameList.size()>1){
            log.error("不存在当前游戏或存在多个重复游戏,无法启动游戏,param:{}", JSON.toJSONString(param));
            return Result.buildResult(ResultEnum.FAILED.getCode(),
                    "不存在当前游戏或存在多个重复游戏，无法启动游戏");
        }
        Game game = gameList.get(0);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", game);
    }

    /**
     * 获取游戏列表
     * @param param
     * @return
     */
    public Result<Game> getGameList(GameParam param){
        List<Game> gameList = gameMapper.getGameList(param);
        return Result.buildResult(ResultEnum.SUCCESSFUL.getCode(), "", gameList);
    }

}
