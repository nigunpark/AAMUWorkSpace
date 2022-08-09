package com.aamu.admin.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThemeController {
	
	//전체 테마 뿌려주기
	@RequestMapping("Theme.do")
	public String themeSelectList() {
		
		
		return "back/theme";
	}

}
