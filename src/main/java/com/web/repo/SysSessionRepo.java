package com.web.repo;

import com.web.entity.SysSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/11
 */
public interface SysSessionRepo extends JpaRepository<SysSession, Long> {

    Optional<SysSession> findBySessionId(String sessionId);

    @Transactional
    @Modifying
    void deleteAllBySessionId(String sessionId);
}
