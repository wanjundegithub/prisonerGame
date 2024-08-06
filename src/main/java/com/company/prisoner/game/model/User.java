package com.company.prisoner.game.model;

import lombok.Builder;
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
    private String role;
    private String className;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;
    private short deleteFlag;

    public static void build(User sourceUser, User targetUser) {
        if(targetUser==null){
            throw new RuntimeException("目标用户属性不为空");
        }
        if(targetUser.getId()!=null){
            throw new RuntimeException("目标用户属性id不为空");
        }
        targetUser.setId(sourceUser.getId());
        targetUser.setNickName(sourceUser.getNickName());
        targetUser.setUserName(sourceUser.getUserName());
        targetUser.setPassword(sourceUser.getPassword());
        targetUser.setRole(sourceUser.getRole());
        targetUser.setClassName(sourceUser.getClassName());
        targetUser.setCreateBy(sourceUser.getCreateBy());
        targetUser.setCreateDate(sourceUser.getCreateDate());
        targetUser.setLastUpdateBy(sourceUser.getLastUpdateBy());
        targetUser.setLastUpdateDate(sourceUser.getLastUpdateDate());
        targetUser.setDeleteFlag(sourceUser.getDeleteFlag());
    }
}
