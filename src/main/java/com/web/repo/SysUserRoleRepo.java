package com.web.repo;

import com.web.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
public interface SysUserRoleRepo extends JpaRepository<SysRole, Long> {
}
