package com.company.prisoner.game.vo;

import com.company.prisoner.game.model.Group;
import com.company.prisoner.game.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserGroupVO {

    private Integer userId;
    private String nickName;
    private String userName;
    private Integer groupSequence;
    private Integer groupId;
    private Integer gameId;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;

    public static UserGroupVO buildUserGroupVO(Group group, User user) {
        UserGroupVO userGroupVO = new UserGroupVO();

        userGroupVO.setUserId(user.getId());
        userGroupVO.setNickName(user.getNickName());
        userGroupVO.setUserName(user.getUserName());

        userGroupVO.setGroupId(group.getId());
        userGroupVO.setGroupSequence(group.getGroupSequence());
        userGroupVO.setGameId(group.getGameId());
        userGroupVO.setCreateBy(group.getCreateBy());
        userGroupVO.setCreateDate(group.getCreateDate());
        userGroupVO.setLastUpdateBy(group.getLastUpdateBy());
        userGroupVO.setLastUpdateDate(group.getLastUpdateDate());

        return userGroupVO;
    }
}
