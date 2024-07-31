package com.company.prisoner.game.service;

import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.GameActiveEnum;
import com.company.prisoner.game.enums.ResultEnum;
import com.company.prisoner.game.mapper.GameMapper;
import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * @author user
 */
@Service
public class GameService {

    @Autowired
    private GameMapper gameMapper;

    /**
     * 创建游戏后马上进行分组
     * @return
     */
    public synchronized Result createGame(){
        Game game = new Game();
        game.setCreateBy(GameConstants.ADMIN);
        game.setLastUpdateBy(GameConstants.ADMIN);
        game.setAliveFlag(GameActiveEnum.INITIAL.getStatus());
        Integer result = gameMapper.insertGame(game);
        if(result>0){
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
        }
        return Result.buildResult(ResultEnum.FAILED.getCode(), "创建游戏失败,请稍后重试");
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
     * 结束游戏
     * @return
     */
    public synchronized Result stopGame(Game game){
        game.setAliveFlag(GameActiveEnum.STOP.getStatus());
        Integer result = gameMapper.updateGame(game);
        if(result>0){
            return Result.buildResult(ResultEnum.SUCCESSFUL.getCode());
        }
        return Result.buildResult(ResultEnum.FAILED.getCode(), "结束游戏失败,请稍后重试");
    }

    /**
     * 获取已处于完成状态的游戏 取最近时间的
     * @return
     */
    public Result<Game> getLatestFinishedGame(){
        List<Game> gameList = gameMapper.getGame(GameActiveEnum.STOP.getStatus());
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

}
