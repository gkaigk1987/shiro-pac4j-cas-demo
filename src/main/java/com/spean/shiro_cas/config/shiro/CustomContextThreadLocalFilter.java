package com.spean.shiro_cas.config.shiro;

import io.buji.pac4j.subject.Pac4jPrincipal;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.spean.shiro_cas.util.ContextHolder;

public class CustomContextThreadLocalFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			Subject subject = SecurityUtils.getSubject();
			PrincipalCollection pcs = subject.getPrincipals();
			if(null !=pcs){
				Pac4jPrincipal p = pcs.oneByType(Pac4jPrincipal.class);
				ContextHolder.setPac4jPrincipal(p);
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			ContextHolder.clear();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
