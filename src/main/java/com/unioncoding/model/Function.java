package com.unioncoding.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/5.
 */
@Entity
public class Function
{
    public Function()
    {

    }

    public Function(String id, String pId)
    {
        this.id = id;

        this.pId = pId;
    }

    @Id
    private String id;

    private String pId;

    private String name;

    private String url;

    private String icon;

    private String match;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "authority_function", joinColumns = {@JoinColumn(name ="function_id")}, inverseJoinColumns = {@JoinColumn(name ="authority")})
    private List<Authority> authorities;

    @Transient
    private List<Function> functions = new ArrayList<>();

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

    public String getMatch()
    {
        return match;
    }

    public void setMatch(String match)
    {
        this.match = match;
    }

    public List<Function> getFunctions()
    {
        return functions;
    }

    public void setFunctions(List<Function> functions)
    {
        this.functions = functions;
    }

    public List<Authority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities)
    {
        this.authorities = authorities;
    }
}
