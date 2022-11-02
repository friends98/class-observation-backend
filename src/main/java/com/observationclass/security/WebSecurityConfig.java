package com.observationclass.security;

import com.observationclass.model.ERole;
import com.observationclass.repository.AccountRepository;
import com.observationclass.security.jwt.AuthTokenFilter;
import com.observationclass.service.AccountService;
import com.observationclass.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountReqository;
    @Autowired
    private RoleService roleService;
    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountService();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthTokenFilter(authenticationManager(), accountReqository))
                .authorizeRequests()
                .antMatchers("/auth/google").permitAll()
                .antMatchers("/api/admin/**").hasAnyAuthority(ERole.ROLE_ADMIN.toString())
                .antMatchers("/api/headTraining/**").hasAnyAuthority(ERole.ROLE_HEAD_TRAINING.toString())
                .antMatchers("/api/headSubject/**").hasAnyAuthority(ERole.ROLE_HEAD_SUBJECT.toString())

                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(new AuthTokenFilter(authenticationManager(),accountReqository), UsernamePasswordAuthenticationFilter.class);
    }
}
