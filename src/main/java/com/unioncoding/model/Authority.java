package com.unioncoding.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@Entity
public class Authority implements GrantedAuthority
{
    @Id
    private String authority;

    @NotEmpty(message = "不能名称为null")
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "authority_function", joinColumns = {@JoinColumn(name ="authority")}, inverseJoinColumns = {@JoinColumn(name ="function_id")})
    private List<Function> functions;

    @Override
    public String getAuthority()
    {
        return authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Function> getFunctions()
    {
        return functions;
    }

    public void setFunctions(List<Function> functions)
    {
        this.functions = functions;
    }
}
