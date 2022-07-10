package com.aamu.aamurest.user.web;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;
import com.aamu.aamurest.user.service.api.KakaoReview;
import com.aamu.aamurest.user.service.api.KakaoReview.CommentInfo;

@RestController
@CrossOrigin("*")
public class MainController {
	
	@Autowired
	private MainService service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("planner/edit")
	public int plannerInsert(PlannerDTO dto) {
		int affected = 0;
		affected = service.plannerInsert(dto);
		List<RouteDTO> routes = dto.getRoute();
		
		service.RouteInsert(routes);
		
		
		return affected;
	}
	
	@PutMapping("planner/edit")
	public int plannerUpdate(PlannerDTO dto) {
		int affected = 0;
		
		affected = service.updatePlanner(dto);
		List<RouteDTO> routes = dto.getRoute();
		
		service.updateRoute(routes);
		
		
		return affected;
	}
	@DeleteMapping("planner/edit")
	public int deletePlanner(@RequestParam Map map) {
		int affected = 0;
		affected = service.deletePlanner(map);
		
		
		return affected;
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
/*
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getContenttypeid()==32) {
				int hotelDay = list.get(i).getDay();
				Collections.swap(list, hotelDay-1, i);
				if(tripDay<hotelDay) tripDay =hotelDay;
			}
			else {
				list.get(i).setMtime(30*1000*60);
			}
		}///////////////list index change
		/////////////////////////
		for(int i=0;i<tripDay;i++) {
			double hotelxy =list.get(i).getDto().getMapx()+list.get(i).getDto().getMapy();
			double low = hotelxy;
			for(int k=tripDay;k<list.size();k++) {
				double xyResult = hotelxy-list.get(k+i).getDto().getMapx()+list.get(k+i).getDto().getMapy();
				if(low>xyResult) {
					low=xyResult;
					Collections.swap(list,tripDay+i,k);
				}
			}
			list.get(tripDay+i).setDay(i);
		}//////////////////hotel most near place index change
		int result = (int)Math.ceil(((double)list.size()-tripDay)/tripDay);
		int count = 0;
		int day=1;
		double attrxy=0;
		/////////////////////////////
		if(list.size()-tripDay*2==1) {
			list.get(list.size()-1).setDay(1);
		}////////////////if
		/////////////////////////////
		
		else {
			for(int i=1;i<tripDay;i++) {
				if(result-1>count) {
					
					if(i==1&&count==0) attrxy = list.get(tripDay+i).getDto().getMapx()+list.get(tripDay+i).getDto().getMapy();
					else  attrxy = list.get(tripDay*(2+count)+i).getDto().getMapx()+list.get((2+count)+i).getDto().getMapy();
					double low = attrxy;
					for(int k=tripDay*(2+count);k<list.size();k++) {
						double resultxy = attrxy-list.get(k).getDto().getMapx()+list.get(k).getDto().getMapy();
						if(low>resultxy) {
							low=resultxy;
							Collections.swap(list,tripDay*(2+count)+i,k);
						}
					}
					list.get(tripDay*(2+count)+i).setDay(day);
					count++;
				}
				else {
					count=0;
					day++;
					attrxy = list.get(tripDay+i).getDto().getMapx()+list.get(tripDay+i).getDto().getMapy();
					list.get(i).setDay(day);
					
					count++;
					
				}//////////
			}////////////for attr most near place
		}////////////////else
*/
		int result = (int)Math.ceil(((double)list.size()-tripDay)/tripDay);
		int count = 0;
		int day=1;

		if(list.size()-tripDay*2==1) {
			for(RouteDTO route:list) {
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
		for(AttractionDTO dto:list) {
			
			if(dto.getKakaokey()!=null) {
				String uri = "http://127.0.0.1:5000/aamu?map="+dto.getKakaokey();
				
				ResponseEntity<KakaoReview> responseEntity = 
						restTemplate.exchange(uri, HttpMethod.GET, null, KakaoReview.class);
				
				
				dto.setStar(responseEntity.getBody().getBasicInfo().getStar());
				list.add(dto);
			}
			
		}
		
		return list;
	}
	
	@GetMapping("info/review")
	public KakaoReview getReview(@RequestParam String kakaoKey) {
		KakaoReview kakaReview = new KakaoReview();
		if(kakaoKey!=null) {
			
			String uri = "http://127.0.0.1:5000/aamu?map="+kakaoKey;
			
			ResponseEntity<KakaoReview> responseEntity = 
					restTemplate.exchange(uri, HttpMethod.GET, null, KakaoReview.class);
			kakaReview = responseEntity.getBody();
		}
		return kakaReview;
		
	}
	
	@GetMapping("/info/search")
	public List<AttractionDTO> search(@RequestParam Map map){
		
		List<AttractionDTO> lists =service.searchTwoPlace(map);
		
		return lists;
	}

}
