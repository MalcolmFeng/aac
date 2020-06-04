package com.inspur.springsecurityoauth_authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setHideUserNotFoundExceptions(false);
        auth.authenticationProvider(authenticationProvider);

        // 测试自定义filter
//        auth.userDetailsService(userDetailsService);
//        auth.authenticationProvider(new UserJoinTimeAuthenticationProvider(userDetailsService));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**", "/icon/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 配置需要角色权限的地址
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/db/**").hasAnyRole("admin","user")
//                .antMatchers("/user/**").access("hasAnyRole('admin','user')")
                // 配置不需要认证的地址
                .antMatchers("/login", "/login-error", "/oauth/authorize", "/oauth/token").permitAll()
                // 配置只需要认证的地址
                .anyRequest().authenticated()
                // 配置登录地址
                .and().formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .permitAll().and()
                .csrf().disable();

        // 自定义filter测试
//        http.addFilterBefore(new CustomerAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

}
