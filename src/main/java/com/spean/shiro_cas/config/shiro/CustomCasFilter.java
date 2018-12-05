package com.spean.shiro_cas.config.shiro;

import static org.pac4j.core.util.CommonHelper.assertNotNull;
import io.buji.pac4j.context.ShiroSessionStore;
import io.buji.pac4j.engine.ShiroSecurityLogic;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.Cookie;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.http.adapter.J2ENopHttpActionAdapter;
import org.springframework.util.StringUtils;

public class CustomCasFilter implements Filter {

	 private SecurityLogic<Object, J2EContext> securityLogic;

	    private Config config;

	    private String clients;

	    private String authorizers;

	    private String matchers;

	    private Boolean multiProfile;

	    public CustomCasFilter() {
	        securityLogic = new ShiroSecurityLogic<>();
	    }

	    @Override
	    public void init(final FilterConfig filterConfig) throws ServletException {}

	    @SuppressWarnings("unchecked")
		@Override
	    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

	        assertNotNull("securityLogic", securityLogic);
	        assertNotNull("config", config);

	        final HttpServletRequest request = (HttpServletRequest) servletRequest;
	        final HttpServletResponse response = (HttpServletResponse) servletResponse;
	        final SessionStore<J2EContext> sessionStore = config.getSessionStore();
	        final J2EContext context = new J2EContext(request, response, sessionStore != null ? sessionStore : ShiroSessionStore.INSTANCE);
	        if(!SecurityUtils.getSubject().isAuthenticated()){
	        	Collection<Cookie> cookies = context.getRequestCookies();
		        Optional<Cookie> fid = cookies.stream().filter(cookie -> "fid".equals(cookie.getName())).findFirst();
		        if(fid.isPresent()&& !StringUtils.isEmpty(fid.get().getValue())) {
		        	//在其他项目中已经登录、跳去登录验证；
		        	 securityLogic.perform(context, config, (ctx, profiles, parameters) -> {

		 	            filterChain.doFilter(request, response);
		 	            return null;

		 	        }, J2ENopHttpActionAdapter.INSTANCE, clients, authorizers, matchers, multiProfile);
		        }
	        }
	        // 不登录也能访问的页面
	        filterChain.doFilter(request, response);
	    }

	    @Override
	    public void destroy() {}

	    public SecurityLogic<Object, J2EContext> getSecurityLogic() {
	        return securityLogic;
	    }

	    public void setSecurityLogic(final SecurityLogic<Object, J2EContext> securityLogic) {
	        this.securityLogic = securityLogic;
	    }

	    public Config getConfig() {
	        return config;
	    }

	    public void setConfig(final Config config) {
	        this.config = config;
	    }

	    public String getClients() {
	        return clients;
	    }

	    public void setClients(final String clients) {
	        this.clients = clients;
	    }

	    public String getAuthorizers() {
	        return authorizers;
	    }

	    public void setAuthorizers(final String authorizers) {
	        this.authorizers = authorizers;
	    }

	    public String getMatchers() {
	        return matchers;
	    }

	    public void setMatchers(final String matchers) {
	        this.matchers = matchers;
	    }

	    public Boolean getMultiProfile() {
	        return multiProfile;
	    }

	    public void setMultiProfile(final Boolean multiProfile) {
	        this.multiProfile = multiProfile;
	    }

}
