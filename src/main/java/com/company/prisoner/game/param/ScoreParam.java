package com.company.prisoner.game.param;

import com.company.prisoner.game.model.Score;
import lombok.Data;

/**
 * @author user
 */
@Data
public class ScoreParam  extends Score {

    private Integer id;

    private String nickName;

    private String userName;

    private String password;

    private String role;

    private String className;

    private Integer pageSize;

    private Integer page;

    private Integer offset;

}
