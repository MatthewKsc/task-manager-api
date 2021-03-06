package com.matthewksc.taskmanager.security;

import com.matthewksc.taskmanager.jwt.JwtConfig;
import com.matthewksc.taskmanager.jwt.TokenVerify;
import com.matthewksc.taskmanager.jwt.UserFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private MyUserDetailsService myUserDetailsService;
    private JwtConfig jwtConfig;

    public WebSecurityConfig(MyUserDetailsService myUserDetailsService, JwtConfig jwtConfig) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.headers().disable(); for h2 database
        http
                .cors().and()
                .addFilter(new UserFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new TokenVerify(jwtConfig), UserFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
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
