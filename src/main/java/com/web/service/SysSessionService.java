package com.web.service;

import com.web.entity.SysSession;
import com.web.repo.SysSessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/11
 */
@Service
@RequiredArgsConstructor
public class SysSessionService {

    private final SysSessionRepo repo;

    public void create(String sessionId,String session){
        SysSession sysSession = SysSession.builder()
                .session(session)
                .sessionId(sessionId)
                .build();
        repo.save(sysSession);
    }

    public Optional<SysSession> findBySessionId(String sessionId){
        return repo.findBySessionId(sessionId);
    }

    public void deleteBySessionId(String sessionId){
        repo.deleteAllBySessionId(sessionId);
    }

    public List<SysSession> findAll(){
        return repo.findAll();
    }
}
