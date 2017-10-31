package com.unioncoding.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by 吴晓冬 on 2017/10/30.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Issue implements Cloneable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long pId;

    private Long projectId;

    private String title;

    @CreatedDate
    @Column(updatable = false)
    private Date createtime;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="creater", updatable = false)
    private SysUser creater;

    @LastModifiedDate
    private Date updatetime;

    @OneToOne
    @LastModifiedBy
    @JoinColumn(name="updater")
    private SysUser updater;

    @OneToOne
    @JoinColumn(name="owner")
    @NotNull(message = "负责人不能为空")
    private SysUser owner;

    private String dscb;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getpId()
    {
        return pId;
    }

    public void setpId(Long pId)
    {
        this.pId = pId;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
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

    public Date getUpdatetime()
    {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    public SysUser getUpdater()
    {
        return updater;
    }

    public void setUpdater(SysUser updater)
    {
        this.updater = updater;
    }

    public SysUser getOwner()
    {
        return owner;
    }

    public void setOwner(SysUser owner)
    {
        this.owner = owner;
    }

    public String getDscb()
    {
        return dscb;
    }

    public void setDscb(String dscb)
    {
        this.dscb = dscb;
    }
}
