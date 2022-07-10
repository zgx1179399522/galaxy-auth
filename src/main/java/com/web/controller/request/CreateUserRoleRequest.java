package com.web.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRoleRequest {

    private String roleCode;
    private String roleName;
}
