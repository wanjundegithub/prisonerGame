package com.company.prisoner.game.model;

import lombok.Data;

import java.util.Date;

/**
 * @author user
 */
@Data
public class User {
    private Integer id;
    private String nickName;
    private String userName;
    private String password;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;
    private short deleteFlag;
}
