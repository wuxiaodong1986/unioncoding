package com.unioncoding.config;

import com.unioncoding.dao.FunctionRepository;
import com.unioncoding.model.Authority;
import com.unioncoding.model.Function;
import com.unioncoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 安全配置
 * Created by 吴晓冬 on 2017/9/1.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private FunctionRepository functionRepository;

    /**
     * 配置过滤规则
     */
    protected void configure(HttpSecurity http) throws Exception
    {
        //获取所有功能信息
        List<Function> functions = functionRepository.findAll();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //设置不登录可访问页面
        registry = registry.antMatchers("/ui/**", "/").permitAll();
        //设置需要登录且需要授权才可以访问的页面
        for (Function function : functions)
        {
            Set<String> authoritySet = new HashSet<>();

            List<Authority> authorities = function.getAuthorities();
            for (Authority authority : authorities)
            {
                authoritySet.add(authority.getAuthority());
            }

            if (!authoritySet.isEmpty() && !StringUtils.isEmpty(function.getMatchUrl()))
            {
                registry = registry.antMatchers(function.getMatchUrl().split(",")).hasAnyAuthority(authoritySet.toArray(new String[authoritySet.size()]));
            }
        }
        //其他页面都需登录才可访问
        registry = registry.anyRequest().authenticated();

        //页面的其他设置
        registry.and().formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/main").permitAll()//设置登录页面
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()//设置注销页面
                .and().csrf().ignoringAntMatchers("/interface/*");//设置免除csrf防御的接口
    }

    /**
     * 配置认证
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);//使用自定义service类来认证
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());//对密码进行加密后再认证
        auth.authenticationProvider(authenticationProvider);
    }

}