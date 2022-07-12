package com.aamu.admin.main.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	
	@RequestMapping(value = "admin.do", method = RequestMethod.GET)
	public String home(Model model) {
		
		Map<String, String> map = new HashMap<>();

		Date current = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		String today = formatter.format(current);
		System.out.println(today);
		model.addAttribute("users",service.usersTotalCount());
		model.addAttribute("todayUsers", service.usersTodayCount(today));
		map.put("places", "placesinfo");
		model.addAttribute("places", service.placesTotalCount(map));
		map.put("places", "hotelinfo");
		model.addAttribute("hotel", service.placesTotalCount(map));
		map.put("places", "dinerinfo");
		model.addAttribute("diner", service.placesTotalCount(map));
		
		return "/main/main";
	}
	
	
}
