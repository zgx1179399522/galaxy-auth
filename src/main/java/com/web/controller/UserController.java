package com.web.controller;

import com.web.controller.request.CreateUserRequest;
import com.web.service.SysUserService;
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
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/user/create")
    public void create(@RequestBody CreateUserRequest request){
        sysUserService.create(request.getUsername(), request.getPassword());
    }
}
