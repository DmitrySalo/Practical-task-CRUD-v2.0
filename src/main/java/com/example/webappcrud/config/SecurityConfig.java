package com.example.webappcrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;

    public SecurityConfig(@Qualifier("userDetailsServiceImp") UserDetailsService userDetailsService,
                          SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()
                .successHandler(successUserHandler)
                .loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll();

        httpSecurity.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        httpSecurity
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/errors/**").permitAll()
                .antMatchers("/user/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers("/admin/**")
                .access("hasAnyRole('ROLE_ADMIN')")
                .anyRequest()
                .authenticated();

        httpSecurity.csrf().disable();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
