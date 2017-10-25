package com.unioncoding.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@Entity
public class SysAuthority implements GrantedAuthority
{
    @Id
    @NotEmpty(message = "角色名不能为空")
    @Size(min = 2,max = 20, message = "角色名长度需在2到20位")
    private String authority;

    @NotEmpty(message = "角色描述不能为空")
    @Size(min = 2,max = 20, message = "角色描述长度需在2到20位")
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_authority_function", joinColumns = {@JoinColumn(name ="authority")}, inverseJoinColumns = {@JoinColumn(name ="function_id")})
    private List<SysFunction> functions;

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

    public List<SysFunction> getFunctions()
    {
        return functions;
    }

    public void setFunctions(List<SysFunction> functions)
    {
        this.functions = functions;
    }
}
