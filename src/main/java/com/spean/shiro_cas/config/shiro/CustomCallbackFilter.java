package com.spean.shiro_cas.config.shiro;

import io.buji.pac4j.filter.CallbackFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * sso登录回调 返回st,单点登录也会post到这个地址，
 * 验证st后跳转
 * http://localhost:8081/callback?client_name=demoClient&ticket=ST-54-AKN6qLpOlwlMgjtP22Yf-sso.foxitreader.cn
 * @author ssss
 *
 */
public class CustomCallbackFilter extends CallbackFilter {

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		super.doFilter(servletRequest, servletResponse, filterChain);
	}
	
}
