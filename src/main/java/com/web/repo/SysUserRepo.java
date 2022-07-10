package com.web.repo;

import com.web.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public interface SysUserRepo extends JpaRepository<SysUser, Long> {

    Optional<SysUser> findByUsername(String username);
}
