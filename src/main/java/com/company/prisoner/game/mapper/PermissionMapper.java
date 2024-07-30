package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Permission;
import org.springframework.stereotype.Repository;

/**
 * @author user
 */
@Repository
public interface PermissionMapper {

    /**
     * 查询对应学号的权限
     * @param schoolNumber
     * @return
     */
    Permission selectRolePermission(String schoolNumber);
}
