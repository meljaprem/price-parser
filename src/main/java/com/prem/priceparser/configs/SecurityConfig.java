package com.prem.priceparser.configs;


import com.prem.priceparser.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/*",
                        "/js/*",
                        "/fonts/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/main", true)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }


    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, UserSecurityService userSecurityService) throws Exception {
        auth
                .userDetailsService(userSecurityService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


}
