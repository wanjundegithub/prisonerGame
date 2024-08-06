package com.company.prisoner.game.vo;

import com.company.prisoner.game.model.Score;
import lombok.Data;

/**
 * @author user
 */
@Data
public class ScoreVO extends Score {

    private String nickName;

    private String userName;

    private String className;

}
