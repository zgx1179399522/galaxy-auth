package com.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;
}
