package com.unioncoding.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@Entity
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name ="id")}, inverseJoinColumns = {@JoinColumn(name ="authority")})
    private List<Authority> authorities;

    @Override
    public List<Authority> getAuthorities()
    {
        return authorities;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    /**
     * 账户是否在有效期内
     * 一直返回有效
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 是未被锁定
     * 一直返回未锁定
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 凭据/密码是否在有效期内
     * 一直返回有效
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities)
    {
        this.authorities = authorities;
    }
}
