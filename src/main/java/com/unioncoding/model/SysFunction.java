package com.unioncoding.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/5.
 */
@Entity
public class SysFunction
{
    public SysFunction()
    {

    }

    public SysFunction(String id, String pId)
    {
        this.id = id;

        this.pId = pId;
    }

    @Id
    @NotEmpty(message = "菜单编号不能为空")
    @Size(min = 2,max = 6, message = "菜单编号长度需在2到6位")
    private String id;

    @NotEmpty(message = "父菜单编号不能为空")
    @Size(min = 1,max = 4, message = "父菜单编号长度需在1到4位")
    private String pId;

    @NotEmpty(message = "菜单名称不能为空")
    @Size(min = 2,max = 10, message = "菜单名称长度需在2到10位")
    private String name;

    private String url;

    private String icon;

    private String matchUrl;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_authority_function", joinColumns = {@JoinColumn(name ="function_id")}, inverseJoinColumns = {@JoinColumn(name ="authority")})
    private List<SysAuthority> authorities;

    @Transient
    private List<SysFunction> functions = new ArrayList<>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getpId()
    {
        return pId;
    }

    public void setpId(String pId)
    {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getMatchUrl()
    {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl)
    {
        this.matchUrl = matchUrl;
    }

    public List<SysFunction> getFunctions()
    {
        return functions;
    }

    public void setFunctions(List<SysFunction> functions)
    {
        this.functions = functions;
    }

    public List<SysAuthority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<SysAuthority> authorities)
    {
        this.authorities = authorities;
    }
}
