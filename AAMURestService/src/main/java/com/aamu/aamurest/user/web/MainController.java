package com.aamu.aamurest.user.web;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@RestController
@CrossOrigin("*")
public class MainController {
	
	@Autowired
	private MainService service;
	
	@PostMapping("planner/edit")
	public int plannerInsert(PlannerDTO dto) {
		int affected = 0;
		int countRoute = 0;
		affected = service.plannerInsert(dto);
		List<RouteDTO> routes = dto.getRoute();
		Map map = new HashMap();

		for(RouteDTO route:routes) {
			service.RouteInsert(route);
			countRoute++;
		}
		
		return 0;
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
	@PostMapping("planner/data")
	public PlannerDTO plannerData(PlannerDTO dto) {
		
		List<RouteDTO> list = dto.getRoute();
		int day = list.get(0).getDay();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getContenttypeid()==32) {
				if(day<list.get(i).getDay()) {
					day=list.get(i).getDay();
				}
			}
		}
		List<Long> startTime=new Vector<>();
		long oneDay = 24*1000*60*60;
		int startDay=1;
		int index=0;
		
		for(RouteDTO routeTime: list) {
			
			if(String.valueOf(routeTime.getStartTime())!=null) {
				startTime.add(routeTime.getStartTime());
			}
		}
		long dayTime = startTime.get(index);
		for(RouteDTO route:list) {
			
			if(String.valueOf(route.getAtime())!=null) {
				dayTime += route.getAtime()+route.getMtime();
				route.setDay(startDay);
				if(dayTime>oneDay) {
					dayTime=0;
					index++;
					if(startDay<=day) {
						startDay++;
					}
					dayTime = startTime.get(index);
				}
			}
		}
		
		return null;
	}
	@GetMapping("planner/selectone")
	public PlannerDTO selectPlannerOne(@RequestParam Map map) {
		
		PlannerDTO dto = new PlannerDTO();
		List<RouteDTO> routes = new Vector<>();
		int rbn = dto.getRbn();

		routes = service.selectRouteList(rbn);
		for(RouteDTO route :routes) {
		}
		
		dto.setRoute(routes);
		
		return dto;
	}
	
	@GetMapping("/info/places")
	public List<AttractionDTO> attractionList(@RequestParam Map map){
		
		List<AttractionDTO> list = new Vector<>();
		
		if(map.get("sigungucode")!=null) {
			switch(map.get("contenttypeid").toString()) {
			case "12":
			case "28":
				list = service.selectAttrSigungu(map);
				break;
			case "15":
				list = service.selectEventSigungu(map);
				break;
			case "32":
				list = service.selectHotelSigungu(map);
				break;
			case "39":
				list = service.selectDinerSigungu(map);
				break;
			}
		}
		else {
			
			switch(map.get("contenttypeid").toString()) {
			case "12":
			case "28":
				list = service.selectPlacesList(map);
				break;
			case "15":
				list = service.selectEventList(map);
				break;
			case "32":
				list = service.selectHotelList(map);
				break;
			case "39":
				list = service.selectDinerList(map);
				break;
			}
		}
		
		return list;
	}

}
