package com.springrest.springrest.entity.enums;

public enum Role {
    UPLOADER,
    CONSUMER,
    ADMIN;

    public static boolean isValid(String value) {
        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static Role from(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}

