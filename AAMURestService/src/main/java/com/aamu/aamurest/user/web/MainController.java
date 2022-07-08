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
import org.springframework.web.bind.annotation.RequestBody;
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
		
		
		return affected;
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
	public PlannerDTO plannerData(@RequestBody PlannerDTO dto) {
		System.out.println(dto.getRoute());
		List<RouteDTO> list = dto.getRoute();
		int tripDay = list.get(0).getDay();

		Map<Integer,List<RouteDTO>> map=new HashMap<>();
		for(RouteDTO route: list) {
			int contentid = route.getContentid();
			AttractionDTO placeInfo = service.selectOnePlace(contentid);
			route.setDto(placeInfo);
			if(route.getDay()!=0) {
				if(tripDay<route.getDay()) {
					tripDay =route.getDay();
				}
			}
		}////////////////////
		int result = (int)Math.ceil(((double)list.size()-tripDay)/tripDay);
		int count = 0;
		int day=1;
		if(list.size()-tripDay*2==1) {
			for(RouteDTO route:list) {
				route.setMtime(30*1000*60);
				if(route.getDay()==0) {
					if(result>count) {
						route.setDay(day);
						count++;
					}
					else {
						day++;
						route.setDay(day);
					}
				}
			}////////////for
		}////////////////if
		
		else {
				for(RouteDTO route:list) {
					route.setMtime(30*1000*60);
					if(route.getDay()==0) {
						if(result>count) {
							route.setDay(day);
							count++;
						}
						else {
							count=0;
							day++;
							route.setDay(day);
							
							count++;
							
						}//////////
					}//////day=0
			
					
			}///////////////for
		}////////////////else
		
		
			
		PlannerDTO routeList = new PlannerDTO();
		routeList.setRoute(list);
		return routeList;
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
				map.put("selecttable", "placesinfo");
				break;
			case "15":
				map.put("selecttable", "eventinfo");
				break;
			case "32":
				map.put("selecttable", "hotelinfo");
				break;
			case "39":
				map.put("selecttable", "dinerinfo");
				break;
			}
			list = service.selectAttrSigungu(map);
		}
		else {
			
			switch(map.get("contenttypeid").toString()) {
			case "12":
			case "28":
				map.put("selecttable", "placesinfo");
				break;
			case "15":
				map.put("selecttable", "eventinfo");
				break;
			case "32":
				map.put("selecttable", "hotelinfo");
				break;
			case "39":
				map.put("selecttable", "dinerinfo");
				break;
			}
			list = service.selectPlacesList(map);
		}
		
		return list;
	}
	
	@GetMapping("/info/search")
	public List<AttractionDTO> search(@RequestParam Map map){
		
		List<AttractionDTO> lists =service.searchTwoPlace(map);
		
		return lists;
	}

}
