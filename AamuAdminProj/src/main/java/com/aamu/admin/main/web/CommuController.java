package com.aamu.admin.main.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aamu.admin.main.service.CommuDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.serviceimpl.CommuServiceImpl;

@Controller
@RequestMapping("/admin/")
public class CommuController {
	
	@Autowired
	private CommuServiceImpl commuService;
	
	@RequestMapping("Commu.do")
	public String list(
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		
		
		
		
		return "commu/commu";
	}

}
