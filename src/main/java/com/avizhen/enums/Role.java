package com.avizhen.enums;


import org.springframework.util.StringUtils;

public enum Role {
    ADMIN,
    USER;


    public static Role defineRole(String roleName) {
        if (StringUtils.isEmpty(roleName)) {
            return null;
        }
        return valueOf(roleName);
    }
}
