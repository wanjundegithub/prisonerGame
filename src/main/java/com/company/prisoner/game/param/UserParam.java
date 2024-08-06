package com.company.prisoner.game.param;

import com.company.prisoner.game.model.User;
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

    private String className;

    private List<String> userNameList;

    private Integer page;

    private Integer pageSize;

    private Integer offset;

    private List<User> userList;
}
