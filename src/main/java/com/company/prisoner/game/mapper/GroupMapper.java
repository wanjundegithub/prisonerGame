package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Group;
import com.company.prisoner.game.param.GroupParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface GroupMapper {

    Integer insertGroup(Group group);

    Integer insertGroupList(List<Group> group);

    List<Group> selectGroupList(GroupParam param);
}
