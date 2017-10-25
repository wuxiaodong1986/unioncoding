package com.unioncoding.service;

import com.unioncoding.dao.SysUserRepository;
import com.unioncoding.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userRepository.findByUsername(username);

        if (null == user)
        {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserDetails userDetails = new UserDetails()
        {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities()
            {
                return user.getAuthorities();
            }

            @Override
            public String getPassword()
            {
                return user.getPassword();
            }

            @Override
            public String getUsername()
            {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired()
            {
                return true;
            }

            @Override
            public boolean isAccountNonLocked()
            {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired()
            {
                return true;
            }

            @Override
            public boolean isEnabled()
            {
                if (null == user.isEnabled())
                {
                    return false;
                }
                return user.isEnabled();
            }
        };

        return userDetails;
    }
}