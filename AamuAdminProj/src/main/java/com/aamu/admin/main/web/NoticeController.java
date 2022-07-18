package com.aamu.admin.main.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aamu.admin.main.serviceimpl.NoticeServiceImpl;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeServiceImpl noticeService;
	
	@RequestMapping("notice.do")
	public String noticeSelectList(
			
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1", required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		

		
		return "";
		
		
	}
	

}
