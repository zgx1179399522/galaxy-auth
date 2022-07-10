package com.web.controller;

import com.web.controller.request.CreateUserRoleRequest;
import com.web.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private final SysUserRoleService sysUserRoleService;

    @PostMapping("/create/user_role")
    public void create(@RequestBody CreateUserRoleRequest request){
        sysUserRoleService.create(request.getRoleCode(), request.getRoleName());
    }
}
