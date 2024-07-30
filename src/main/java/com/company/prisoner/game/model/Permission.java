package com.company.prisoner.game.model;

import lombok.Data;

import java.util.Date;

/**
 * @author user
 */
@Data
public class Permission {

    private Integer id;

    private String role;

    private String schoolNumber;

    private String createBy;

    private Date createDate;

    private String lastUpdateBy;

    private Date lastUpdateDate;

    private Integer deleteFlag;
}
