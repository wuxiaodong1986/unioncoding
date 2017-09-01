package com.unioncoding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * 安全配置
 * Created by 吴晓冬 on 2017/9/1.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/ui/**", "/").permitAll()//不登录可访问页面
                .antMatchers("/admin-form.html").hasAnyAuthority("admin")//设置需要admin权限才可访问的页面
                .anyRequest().authenticated()//其他页面都需登录才可访问
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/users/userList?pageNumberStr=0").permitAll()//设置登录页面
                .and().logout().permitAll()//设置注销页面
                .and().csrf().ignoringAntMatchers("/interface/*");//设置免除csrf防御的接口
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username,password, enabled from user where username=?")
            .authoritiesByUsernameQuery("select username, authority from user_authority where username=?");
    }
}