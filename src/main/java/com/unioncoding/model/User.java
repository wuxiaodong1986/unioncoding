package com.unioncoding.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
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

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 2,max = 20, message = "用户名长度需在2到20位")
    private String username;

    private String password;

    private Boolean enabled;

    @NotEmpty(message = "姓名不能为空")
    @Size(min = 2,max = 10, message = "姓名长度需在2到10位")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    @Size(min = 11,max = 20, message = "手机号长度需在11到20位")
    private String phone;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱不符合规范")
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
        if (StringUtils.isEmpty(username))
        {
            username = null;
        }
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
        if (StringUtils.isEmpty(name))
        {
            name = null;
        }
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
