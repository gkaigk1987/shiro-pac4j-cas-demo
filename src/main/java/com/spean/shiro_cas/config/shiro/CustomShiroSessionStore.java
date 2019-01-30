package com.spean.shiro_cas.config.shiro;

import io.buji.pac4j.context.ShiroSessionStore;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.session.SessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该实现和io.buji.pac4j.context.ShiroSessionStore一致，但是修改了getTrackableSession和buildFromTrackableSession方法
 * 
 * @author Administrator
 *
 */
public class CustomShiroSessionStore implements SessionStore<J2EContext> {

	private final static Logger logger = LoggerFactory.getLogger(ShiroSessionStore.class);

	public final static CustomShiroSessionStore INSTANCE = new CustomShiroSessionStore();

	/**
	 * Get the Shiro session (do not create it if it does not exist).
	 *
	 * @param createSession
	 *            create a session if requested
	 * @return the Shiro session
	 */
	protected Session getSession(final boolean createSession) {
		return SecurityUtils.getSubject().getSession(createSession);
	}

	@Override
	public String getOrCreateSessionId(final J2EContext context) {
		final Session session = getSession(false);
		if (session != null) {
			return session.getId().toString();
		}
		return null;
	}

	@Override
	public Object get(final J2EContext context, final String key) {
		final Session session = getSession(false);
		if (session != null) {
			return session.getAttribute(key);
		}
		return null;
	}

	@Override
	public void set(final J2EContext context, final String key, final Object value) {
		final Session session = getSession(true);
		if (session != null) {
			try {
				session.setAttribute(key, value);
			} catch (final UnavailableSecurityManagerException e) {
				logger.warn("Should happen just once at startup in some specific case of Shiro Spring configuration",
						e);
			}
		}
	}

	@Override
	public boolean destroySession(final J2EContext context) {
		getSession(true).stop();
		return true;
	}

	@Override
	public Object getTrackableSession(J2EContext context) {
		return getSession(true);
	}

	@Override
	public SessionStore<J2EContext> buildFromTrackableSession(J2EContext context, Object trackableSession) {
		if (trackableSession != null) {
			return new ShiroProvidedSessionStore((Session) trackableSession);
		}
		return null;
	}

	@Override
	public boolean renewSession(J2EContext context) {
		return false;
	}

}
