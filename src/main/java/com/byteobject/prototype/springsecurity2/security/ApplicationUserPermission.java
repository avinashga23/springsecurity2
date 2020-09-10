package com.byteobject.prototype.springsecurity2.security;

public enum  ApplicationUserPermission {
    USER_READ("user:read"),
    USER_READ_WRITE("user:read_write"),
    DEPARTMENT_READ("department:read"),
    DEPARTMENT_WRITE("department:read_write");

    private String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
