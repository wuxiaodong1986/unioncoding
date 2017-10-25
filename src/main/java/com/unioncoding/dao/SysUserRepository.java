package com.unioncoding.dao;

import com.unioncoding.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long>
{
    public SysUser findByUsername(String username);
}
