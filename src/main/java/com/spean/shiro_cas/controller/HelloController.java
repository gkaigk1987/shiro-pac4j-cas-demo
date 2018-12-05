package com.spean.shiro_cas.controller;

import io.buji.pac4j.subject.Pac4jPrincipal;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spean.shiro_cas.util.ContextHolder;

@RestController
public class HelloController {

	@RequestMapping("hello")
	public Object sayHi() {
		
		Pac4jPrincipal p = ContextHolder.getPac4jPrincipal();
		return "hello now:"+System.currentTimeMillis()+"  name="+p.getName();
	}
	@RequestMapping("userInfo")
	public Object getUserInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ret", 200);
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection pcs = subject.getPrincipals();
		if(null !=pcs){
			Pac4jPrincipal p = pcs.oneByType(Pac4jPrincipal.class);
			if(p!=null){
				CommonProfile profile = p.getProfile();
				map.put("profile", profile);
			}
		}
		return map;
	}
	
}
