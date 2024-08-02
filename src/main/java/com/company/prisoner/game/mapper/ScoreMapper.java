package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Score;
import com.company.prisoner.game.param.ScoreParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreMapper {

    List<Score> getScoreList(ScoreParam scoreParam);

    void insertScore(Score score);

    void batchInsertScore(List<Score> scoreList);
}
