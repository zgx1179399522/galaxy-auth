package com.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sys_url")
public class SysUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "url_role",joinColumns = @JoinColumn(name = "url_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<SysRole> roles;
}
