package com.company.prisoner.game.controller;

import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.param.GameParam;
import com.company.prisoner.game.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prisoner/game")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/createGame")
    public Result createGame(){
        log.info("创建游戏");
        return gameService.createGame();
    }

    @PostMapping("/startGame")
    public Result startGame(@RequestBody GameParam gameParam){
        log.info("开始游戏");
        if(gameParam.getGameId()==null){
            log.error("game id为空");
            throw new RuntimeException("game id为空");
        }
        return gameService.startGame(gameParam);
    }

    @PostMapping("/stopGame")
    public Result stopGame(@RequestBody GameParam gameParam){
        log.info("停止游戏");
        if(gameParam.getGameId()==null){
            log.error("game id为空");
            throw new RuntimeException("game id为空");
        }
        return gameService.stopGame(gameParam);
    }

    @GetMapping("/latestGame")
    public Result<Game> getLatestFinishedGame(){
        return gameService.getLatestFinishedGame();
    }
}
