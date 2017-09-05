package com.unioncoding.dao;

import com.unioncoding.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserRepositoryTest
{
    @Autowired
    private SysUserRepository sysUserRepository;


    public void test()
    {
        List<SysUser> sysUsers = sysUserRepository.findAll();
        System.out.println(sysUsers);
    }
    @Test
    public void test1()
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoder = passwordEncoder.encode("111111");
        System.out.println(encoder);
    }
}