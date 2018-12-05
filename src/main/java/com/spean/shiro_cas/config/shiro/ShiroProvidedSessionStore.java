package com.spean.shiro_cas.config.shiro;

import io.buji.pac4j.context.ShiroSessionStore;

import org.apache.shiro.session.Session;

public class ShiroProvidedSessionStore extends ShiroSessionStore {

    /**存储的TrackableSession，往后要操作时用这个session操作*/
    private Session session;

    public ShiroProvidedSessionStore(Session session) {
        this.session = session;
    }
    @Override
    protected Session getSession(final boolean createSession) {
        return session;
    }
}