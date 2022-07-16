package com.aamu.admin.main.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aamu.admin.main.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	private MainService service;
	
	@GetMapping("main.do")
	public String home(Model model) {
		
		
		Map map = service.placesTotalCount();
		Map chartMap = service.selectWeek();		
		
		model.addAttribute("users",service.usersTotalCount());
		model.addAttribute("todayUsers", service.usersTodayCount());

		model.addAttribute("attraction",map.get("attraction"));	
		model.addAttribute("hotel",map.get("hotel"));
		model.addAttribute("diner",map.get("diner"));
		model.addAttribute("event",map.get("event"));
		
		model.addAttribute("usersWeek",chartMap.get("userWeek"));
		model.addAttribute("date",chartMap.get("date"));
		model.addAttribute("join",chartMap.get("join"));
		model.addAttribute("commu",chartMap.get("commu"));
		model.addAttribute("bbs",chartMap.get("bbs"));
		model.addAttribute("planner",chartMap.get("planner"));
		return "/main/main";
	}
	
	
}
