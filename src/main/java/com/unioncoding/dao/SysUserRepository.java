package com.unioncoding.dao;

import com.unioncoding.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long>
{
    public SysUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update SysUser u set u.name=?1, u.phone=?2, u.email=?3 where u.id=?4")
    public int updateForPersonal(String name, String phone, String email, Long id);

    @Modifying
    @Transactional
    @Query(value = "update SysUser u set u.password=?1 where u.id=?2")
    public int updatePassword(String password, Long id);
}