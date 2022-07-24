package com.web.config;

import com.web.SerializableUtils;
import com.web.entity.SysSession;
import com.web.service.SysSessionService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/11
 */
@Component
@RequiredArgsConstructor
public class ShiroSessionDAO extends EnterpriseCacheSessionDAO {

    private final SysSessionService sysSessionService;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = this.generateSessionId(session);
        this.assignSessionId(session, serializable.toString());
        sysSessionService.create(serializable.toString(), SerializableUtils.serializ(session));
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        SysSession sysSession = sysSessionService.findBySessionId(serializable.toString()).orElse(null);
        return sysSession == null ? null : SerializableUtils.deserializ(sysSession.getSession());
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //
    }

    @Override
    public void delete(Session session) {
        sysSessionService.deleteBySessionId(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        List<SysSession> sysSessions = sysSessionService.findAll();
        List<Session> sessions = sysSessions.stream()
                .map(sysSession -> SerializableUtils.deserializ(sysSession.getSession()))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(sessions) ? Collections.emptyList() : Collections.unmodifiableCollection(sessions);
    }
}
