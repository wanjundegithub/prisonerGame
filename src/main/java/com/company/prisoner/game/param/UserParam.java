package com.company.prisoner.game.param;

import lombok.Data;

import java.util.List;

/**
 * @author user
 */
@Data
public class UserParam {

    private Integer id;

    private String userName;

    private String password;

    private String nickName;

    private String role;

    private List<String> userNameList;
}
