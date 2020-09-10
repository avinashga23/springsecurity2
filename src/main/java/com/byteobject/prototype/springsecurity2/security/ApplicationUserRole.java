package com.byteobject.prototype.springsecurity2.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.byteobject.prototype.springsecurity2.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(new HashSet<>(Arrays.asList(USER_READ, USER_READ_WRITE, DEPARTMENT_READ))),
    ADMIN(new HashSet<>(Arrays.asList(USER_READ, USER_READ_WRITE, DEPARTMENT_READ, DEPARTMENT_WRITE)));

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        var authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
