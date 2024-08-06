package com.company.prisoner.game.enums;

import java.util.Arrays;

/**
 * @author user
 */

public enum UserRoleEnum {

    ADMIN("admin"),

    NORMAL("normal");

    private String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static boolean validateUserRole(String inputRole) {
        UserRoleEnum[] roles = UserRoleEnum.values();
        return Arrays.stream(roles)
                .map(UserRoleEnum::getRole)
                .anyMatch(role -> role.equals(inputRole));
    }

}
