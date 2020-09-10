package com.byteobject.prototype.springsecurity2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.byteobject.prototype.springsecurity2.security.ApplicationUserRole.ADMIN;
import static com.byteobject.prototype.springsecurity2.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/login/**", "/logout", "index").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder().username("user1").password(passwordEncoder().encode("user1"))
                .authorities(USER.getGrantedAuthorities()).build();
        UserDetails user2 = User.builder().username("user2").password(passwordEncoder().encode("user2"))
                .authorities(USER.getGrantedAuthorities()).build();
        UserDetails user3 = User.builder().username("user3").password(passwordEncoder().encode("user3"))
                .authorities(USER.getGrantedAuthorities()).build();
        UserDetails user4 = User.builder().username("user4").password(passwordEncoder().encode("user4"))
                .authorities(USER.getGrantedAuthorities()).build();
        UserDetails user5 = User.builder().username("user5").password(passwordEncoder().encode("user5"))
                .authorities(USER.getGrantedAuthorities()).build();

        UserDetails admin1 = User.builder().username("admin1").password(passwordEncoder().encode("admin1"))
                .authorities(ADMIN.getGrantedAuthorities()).build();
        UserDetails admin2 = User.builder().username("admin2").password(passwordEncoder().encode("admin2"))
                .authorities(ADMIN.getGrantedAuthorities()).build();

        return new InMemoryUserDetailsManager(user1, user2, user3, user4, user5, admin1, admin2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void setJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
}
