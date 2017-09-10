package com.unioncoding.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@Entity
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    @NotEmpty(message = "姓名不能为空")
    private String name;

    private String phone;

    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name ="id")}, inverseJoinColumns = {@JoinColumn(name ="authority")})
    private List<Authority> authorities;

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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
//        if (StringUtils.isEmpty(username))
//        {
//            username = null;
//        }
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities)
    {
        this.authorities = authorities;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
//        if (StringUtils.isEmpty(name))
//        {
//            name = null;
//        }
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
