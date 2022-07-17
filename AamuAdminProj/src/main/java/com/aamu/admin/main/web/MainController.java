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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		model.addAttribute("places",map.get("places"));
		model.addAttribute("attraction",map.get("attraction"));	
		model.addAttribute("hotel",map.get("hotel"));
		model.addAttribute("diner",map.get("diner"));
		model.addAttribute("event",map.get("event"));
		
		model.addAttribute("commuCount",map.get("commuCount"));
		model.addAttribute("bbsCount",map.get("bbsCount"));
		model.addAttribute("plannerCount",map.get("plannerCount"));
		
		model.addAttribute("usersWeek",chartMap.get("userWeek"));
		model.addAttribute("date",chartMap.get("date"));
		model.addAttribute("join",chartMap.get("join"));
		model.addAttribute("commu",chartMap.get("commu"));
		model.addAttribute("bbs",chartMap.get("bbs"));
		model.addAttribute("planner",chartMap.get("planner"));
		return "/main/main";
	}
	@GetMapping("userstatstart.do")
	public String getUserStat(Model model) {
		
		
		Map map = service.placesTotalCount();
		Map chartMap = service.selectWeek();		
		
		model.addAttribute("users",service.usersTotalCount());
		model.addAttribute("todayUsers", service.usersTodayCount());
		
		model.addAttribute("usersWeek",chartMap.get("userWeek"));

		return "/main/statistics";
	}
	
	@PostMapping("userstatend.do")
	@ResponseBody
	public Map userStat(@RequestBody Map map) {

		String start = map.get("start").toString().split("T15")[0];
		String end = map.get("end").toString().split("T15")[0];
		
		map.replace("start", start);
		map.replace("end", end);
		
		
		return map;
		
	}
	
	
}
