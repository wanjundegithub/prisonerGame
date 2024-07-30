package com.company.prisoner.game.service;

import com.company.prisoner.game.mapper.PermissionMapper;
import com.company.prisoner.game.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author user
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Permission selectRolePermission(String schoolNumber){
        return permissionMapper.selectRolePermission(schoolNumber);
    }
}
