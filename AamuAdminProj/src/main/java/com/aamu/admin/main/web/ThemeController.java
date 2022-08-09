package com.aamu.admin.main.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.ThemeDTO;
import com.aamu.admin.main.service.UsersDTO;
import com.aamu.admin.main.serviceimpl.ThemeServiceImpl;



@Controller
public class ThemeController {
	
	@Autowired
	private ThemeServiceImpl themeService;
	
	//전체 테마 뿌려주기
	@RequestMapping("Theme.do")
	public String themeSelectList(@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		
		ListPagingData<ThemeDTO> listPagingData= themeService.themeSelectList(map, req, nowPage);
		
		
		return "back/theme";
	}

}
