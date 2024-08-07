package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Score;
import com.company.prisoner.game.param.ScoreParam;
import com.company.prisoner.game.vo.ScoreVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface ScoreMapper {

    List<Integer> getAllGameIdList(ScoreParam scoreParam);

    List<String> getAllClassNameList(ScoreParam scoreParam);

    Integer getScoreCount(ScoreParam scoreParam);

    List<ScoreVO> getScoreList(ScoreParam scoreParam);

    void insertScore(Score score);

    void batchInsertScore(List<Score> scoreList);
}
