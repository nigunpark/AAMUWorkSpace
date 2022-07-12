package com.aamu.admin.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class CommuController {
	
	@RequestMapping("Commu.do")
	public String list() {
		
		return "commu/commu";
	}

}
