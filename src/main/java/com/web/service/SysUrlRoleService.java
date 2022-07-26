package com.web.service;

import com.web.entity.SysUrl;
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

    public List<SysUrl> findAll() {
        return repo.findAll();
    }
}
