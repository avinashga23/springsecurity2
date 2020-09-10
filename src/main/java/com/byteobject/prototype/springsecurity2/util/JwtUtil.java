package com.byteobject.prototype.springsecurity2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("jwtUtil")
public class JwtUtil {

    private Key key;

    private String keyString;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor("this_is_a_random_key_for_jwt_this_string_should_be_longer_than_224_bits".getBytes());
    }

    public String constructJWT(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                .claim("authorities", user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toSet()))
                .setIssuedAt(new Date()).signWith(key).compact();
    }

    public Jws<Claims> parseClaims(String jws) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
    }

    public String extractUserName(Jws<Claims> claims) {
        return claims.getBody().getSubject();
    }

    public List<? extends GrantedAuthority> extractGrantedAuthorities(Jws<Claims> claims) {
        var authorities = claims.getBody().get("authorities", List.class);
        return (List<? extends GrantedAuthority>) authorities.stream().map(auth -> new SimpleGrantedAuthority(auth.toString())).collect(Collectors.toList());
    }

    public boolean isExpired(Jws<Claims> claims) {
        return claims.getBody().getExpiration().after(new Date());
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }
}
