package net.keepsoft.controller;


import javax.annotation.Resource;

import net.keepsoft.server.BaseServer;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	@Resource
	private BaseServer bs;

	public BaseServer getBs() {
		return bs;
	}

}
