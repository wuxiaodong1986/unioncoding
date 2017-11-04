package com.unioncoding.config;

import com.unioncoding.model.SysUser;
import com.unioncoding.service.UserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 审计配置
 * Created by 吴晓冬 on 2017/10/25.
 */

@Component
@EnableJpaAuditing
public class JpaAuditingConfig implements AuditorAware<SysUser>
{
    @Override
    public SysUser getCurrentAuditor()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        UserService.SysUserDetails userDetails = (UserService.SysUserDetails)authentication.getPrincipal();
        return userDetails.getUser();
    }
}
