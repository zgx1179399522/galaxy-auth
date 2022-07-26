package com.web.service;

import com.web.entity.SysRole;
import com.web.repo.SysUserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleService {

    private final SysUserRoleRepo repo;

    public void create(String roleCode, String roleName) {
        SysRole userRole = SysRole.builder()
                .roleCode(roleCode)
                .roleName(roleName)
                .build();
        repo.save(userRole);
    }
}
