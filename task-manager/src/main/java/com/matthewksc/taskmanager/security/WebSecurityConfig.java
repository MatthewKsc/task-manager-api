package com.matthewksc.taskmanager.security;

import com.matthewksc.taskmanager.jwt.TokenVerify;
import com.matthewksc.taskmanager.jwt.UserFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private MyUserDetailsService myUserDetailsService;

    public WebSecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    CorsFilter corsFilter(){
        CorsFilter filter = new CorsFilter(); //this filter is for request from angular (before this
        return filter;                       //i was getting CORS Exception)
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll();
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .addFilter(new UserFilter(authenticationManager()))
                .addFilterAfter(new TokenVerify(), UserFilter.class)
                .authorizeRequests()
                .antMatchers("/task/**").authenticated()
                .antMatchers("/tasks/lists").authenticated()
                .antMatchers("/tasks/lists/**").authenticated()
                .antMatchers("/user/**").authenticated()
                .and()
                    .formLogin()
                .and()
                    .logout()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
