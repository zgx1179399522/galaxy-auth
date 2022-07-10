package com.web.service;

import com.web.entity.SysUrlRole;
import com.web.repo.SysUrlRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Service
@RequiredArgsConstructor
public class SysUrlRoleService {

    private final SysUrlRoleRepo repo;

    public List<SysUrlRole> findAll() {
        return repo.findAll();
    }
}
