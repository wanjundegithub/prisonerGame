package com.company.prisoner.game.controller;

import com.company.prisoner.game.model.Permission;
import com.company.prisoner.game.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prisoner/game/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/role")
    public Permission getUserPermission(@RequestParam String  schoolNumber){
        if(StringUtils.isEmpty(schoolNumber)){
            throw new RuntimeException("schoolNumber is empty");
        }
        return permissionService.selectRolePermission(schoolNumber);
    }
}
