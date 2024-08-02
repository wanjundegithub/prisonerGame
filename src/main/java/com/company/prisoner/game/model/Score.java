package com.company.prisoner.game.model;

import com.company.prisoner.game.constant.GameConstants;
import com.company.prisoner.game.enums.OptionEnum;
import com.company.prisoner.game.vo.UserGroupVO;
import lombok.Data;

import java.util.Date;

/**
 * @author user
 */
@Data
public class Score {
    private Integer id;
    private Integer gameId;
    private Integer groupId;
    private Integer userId;
    private String optionValue;
    private Integer score;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;
    private Integer deleteFlag;

    public static Score build(int scoreCount, UserGroupVO userGroupVO){
        Score score = new Score();
        score.setGameId(userGroupVO.getGameId());
        score.setGroupId(userGroupVO.getGroupId());
        score.setUserId(userGroupVO.getUserId());
        score.setOptionValue(OptionEnum.UN_CHOOSE.getDesc());
        score.setScore(scoreCount);

        score.setCreateBy(GameConstants.ADMIN);
        score.setLastUpdateBy(GameConstants.ADMIN);
        return score;
    }
    public static Score build(int scoreCount, UserGroupVO userGroupVO, Option option){
        Score score = new Score();
        score.setGameId(userGroupVO.getGameId());
        score.setGroupId(userGroupVO.getGroupId());
        score.setUserId(userGroupVO.getUserId());
        score.setOptionValue(OptionEnum.getDescByCode(option.getSelectOption()));
        score.setScore(scoreCount);

        score.setCreateBy(GameConstants.ADMIN);
        score.setLastUpdateBy(GameConstants.ADMIN);
        return score;
    }
}
