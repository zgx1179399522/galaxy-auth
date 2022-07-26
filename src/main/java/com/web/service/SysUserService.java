package com.web.service;

import com.web.entity.SysRole;
import com.web.entity.SysUser;
import com.web.repo.SysUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Service
@RequiredArgsConstructor
public class SysUserService {

    private final SysUserRepo repo;

    public Optional<SysUser> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<SysUser> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public void create(String username, String password) {
        SysUser sysUser = SysUser.builder()
                .username(username)
                .password(password)
                .roles(Collections.singletonList(
                        SysRole.builder()
                                .id(2L)
                                .build()
                ))
                .build();

        repo.save(sysUser);
    }


    public void delete(Long id){
        repo.deleteById(id);
    }
}
