package com.unioncoding.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 吴晓冬 on 2017/10/25.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    @CreatedDate
    @Column(updatable = false)
    private Date createtime;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="creater", updatable = false)
    private SysUser creater;

    @OneToOne
    @JoinColumn(name="owner")
    private SysUser owner;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public SysUser getCreater()
    {
        return creater;
    }

    public void setCreater(SysUser creater)
    {
        this.creater = creater;
    }

    public SysUser getOwner()
    {
        return owner;
    }

    public void setOwner(SysUser owner)
    {
        this.owner = owner;
    }
}
