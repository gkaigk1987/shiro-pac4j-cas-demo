package com.spean.shiro_cas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	@RequestMapping(value = "")
	public String index() {
		return "test";
	}

}
