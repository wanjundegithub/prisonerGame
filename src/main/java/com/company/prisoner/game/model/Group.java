package com.company.prisoner.game.model;

import lombok.Data;

import java.util.Date;

/**
 * @author user
 */
@Data
public class Group {

    private Integer id;
    private Integer groupSequence;
    private Integer gameId;
    private Integer userIdFirst;
    private Integer userIdSecond;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;
    private int deleteFlag;
}
