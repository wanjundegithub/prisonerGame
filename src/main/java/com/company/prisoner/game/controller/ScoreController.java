package com.company.prisoner.game.controller;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.model.PageResult;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.model.Score;
import com.company.prisoner.game.param.ScoreParam;
import com.company.prisoner.game.service.ScoreService;
import com.company.prisoner.game.vo.ScoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author user
 */
@RestController
@RequestMapping("/prisoner/game/score")
@Slf4j
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/getAllScores")
    public Result<List<ScoreVO>> getAllScoreList(@RequestBody ScoreParam scoreParam){
        if(scoreParam.getGameId()==null){
            log.error("游戏不存在, scoreParam:{}", JSON.toJSONString(scoreParam));
            throw new RuntimeException("游戏不存在");
        }
        return scoreService.getAllScores(scoreParam);
    }

    @PostMapping("/getAllGameIdList")
    public Result<List<Integer>> getAllGameIdList(){
        return scoreService.getAllGameIdList();
    }

    @PostMapping("/getPageScoreList")
    public Result<PageResult<Score>> getPageScoreList(@RequestBody ScoreParam scoreParam){
        if(scoreParam.getGameId()==null || scoreParam.getPageSize()==null|| scoreParam.getPage()==null){
            log.error("游戏不存在或者分页数据不存在, scoreParam:{}", JSON.toJSONString(scoreParam));
            throw new RuntimeException("游戏不存在或者分页数据不存在");
        }
        return scoreService.getPageScoreList(scoreParam);
    }

}
