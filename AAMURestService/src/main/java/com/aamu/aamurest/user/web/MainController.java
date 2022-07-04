package com.aamu.aamurest.user.web;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.PlannerDTO.Route;

@RestController
@CrossOrigin
public class MainController {
	
	@Autowired
	private MainService service;
	
	@PostMapping("planner/edit")
	public Map plannerInsert(PlannerDTO dto) {
		int affected = 0;
		int countRoute = 0;
		affected = service.plannerInsert(dto);
		List<Route> routes = dto.getRoute();
		
		for(Route route:routes) {
			service.RouteInsert(route);
			countRoute++;
		}
		
		Map map = new HashMap();
		map.put("planner",affected);
		map.put("route",countRoute);
		return map;
	}
	
	@PutMapping("planner/edit")
	public Map plannerUpdate(PlannerDTO dto) {
		int affected = 0;
		
		
		Map map = new HashMap();
		map.put("result",affected);
		
		return map;
	}
	@DeleteMapping("planner/edit")
	public Map plannerDelete(PlannerDTO dto) {
		int affected = 0;
		
		
		Map map = new HashMap();
		map.put("result",affected);
		
		return map;
	}
}
