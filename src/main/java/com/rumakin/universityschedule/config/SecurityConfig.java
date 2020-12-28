package com.rumakin.universityschedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.rumakin.universityschedule.model.enums.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/*").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
        .antMatchers(HttpMethod.GET, "/*/delete/").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated().and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder().username("admin").password(passwordEncoder().encode("admin")).roles(Role.ADMIN.name())
                        .build(),
                User.builder().username("user").password(passwordEncoder().encode("user")).roles(Role.USER.name())
                        .build());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
