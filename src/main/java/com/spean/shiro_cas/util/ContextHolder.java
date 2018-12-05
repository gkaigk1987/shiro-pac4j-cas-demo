package com.spean.shiro_cas.util;

import io.buji.pac4j.subject.Pac4jPrincipal;

/**
 * 线程内提供  Pac4jPrincipal 访问
 * @author ssss
 *
 */
public class ContextHolder {

	private static final ThreadLocal<Pac4jPrincipal> threadLocal = new ThreadLocal<Pac4jPrincipal>();
	
	public static void setPac4jPrincipal(final Pac4jPrincipal pac4jPrincipal) {
		threadLocal.set(pac4jPrincipal);
	}
	
	public static Pac4jPrincipal getPac4jPrincipal() {
		return threadLocal.get();
	}
	
	public static void clear() {
        threadLocal.set(null);
    }
}
