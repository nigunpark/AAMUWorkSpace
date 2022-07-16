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
	
	@PostMapping("/planner/edit")
	public int plannerInsert(@RequestBody PlannerDTO dto) {
		

		return service.plannerInsert(dto);
	}
	
	@PutMapping("/planner/edit")
	public int plannerUpdate(@RequestBody PlannerDTO dto) {
		
		
		return service.updatePlanner(dto);
	}
	@DeleteMapping("/planner/edit")
	public int deletePlanner(@RequestParam int rbn) {
		int affected = 0;
		affected = service.deletePlanner(rbn);
		
		
		return affected;
	}
	@PostMapping("/planner/data")
	public PlannerDTO plannerData(@RequestBody PlannerDTO dto) {
		List<RouteDTO> list = dto.getRoute();
		int tripDay = list.get(0).getDay();
		int setDay =0;
		Map<Integer,List<RouteDTO>> map=new HashMap<>();
		
		for(int i=0;i<list.size();i++) {
			int contentid = list.get(i).getContentid();
			AttractionDTO placeInfo = service.selectOnePlace(contentid);
			list.get(i).setDto(placeInfo);
			
			if(list.get(i).getDay()!=0) {
				int hotelDay = list.get(i).getDay();
				Collections.swap(list, hotelDay-1, i);
				if(tripDay<hotelDay) tripDay =hotelDay;
				
			}
			else {
				list.get(i).setMtime(30*1000*60);
			}
		}///////////////list index change
		/////////////////////////
		list.get(tripDay-1).setContentid(list.get(tripDay-2).getContentid());
		list.get(tripDay-1).setContenttypeid(list.get(tripDay-2).getContenttypeid());
		for(RouteDTO route:list) {
			int contentid = route.getContentid();
			AttractionDTO placeInfo = service.selectOnePlace(contentid);
			route.setDto(placeInfo);
			if(route.getContenttypeid()!=32) {
				route.setMtime(1000*60*30);
			}

		}
		/*
		for(int i=0;i<tripDay;i++) {
			
			double hotelxy =list.get(i).getDto().getMapx()+list.get(i).getDto().getMapy();
			double low = 0;
			for(int k=tripDay;k<list.size();k++) {
				
				if(k+i==list.size()) break;
				double xyResult = hotelxy-list.get(k+i).getDto().getMapx()+list.get(k+i).getDto().getMapy();
				
				if(low<xyResult) {
					low=xyResult;
					Collections.swap(list,tripDay+i,k+i);
				}
			}
			
			list.get(tripDay+i).setDay(i+1);

		}//////////////////hotel most near place index change
		*/
		int result = (int)Math.ceil(((double)list.size()-tripDay)/tripDay);
		int index=0;
		int count = 0;

		/////////////////////////////
		

		if(list.size()-tripDay*2==1) {
			list.get(list.size()-1).setDay(1);
		}////////////////if
		/////////////////////////////
		
		else {
			while(true){
				
				if(count<result) {
					double standardx = list.get(count*tripDay+index).getDto().getMapx();
					double standardy = list.get(count*tripDay+index).getDto().getMapy();
					//System.out.println("기준장소:"+list.get(count*tripDay+index).getDto().getTitle());
					double low = 100;
					double attrx =0;
					double attry =0;
					double resultxy=0;
					for(int k=tripDay;k<list.size();k++) {
						
						if(list.get(k).getDay()==0) {
							
							//System.out.println(k);
							attrx = list.get(k).getDto().getMapx();
							attry = list.get(k).getDto().getMapy();
							resultxy = Math.sqrt(Math.pow(Math.abs(standardx-attrx),2)+Math.pow(Math.abs(standardy-attry),2));
							System.out.println(String.format("비교장소:%s, 비교결과:%s",list.get(k).getDto().getTitle(),resultxy));
							if(low>resultxy && tripDay*(count+1)+index<list.size()) {
								
								low=resultxy;
								
								
								//System.out.println("low:"+low);
								//System.out.println(String.format("swap할 인덱스 tripday:%s,index:%s,주소:%s",tripDay*(count+1)+index,k,list.get(k).getDto().getTitle()));
								
							}
				
						}
						if(k==list.size()-1) {
							for(int j=tripDay;j<list.size();j++) {
								attrx = list.get(j).getDto().getMapx();
								attry = list.get(j).getDto().getMapy();
								resultxy = Math.sqrt(Math.pow(Math.abs(standardx-attrx),2)+Math.pow(Math.abs(standardy-attry),2));
								if(low==resultxy) {Collections.swap(list,j,tripDay*(count+1)+index); break;} 
							}
						}
						
					}
					if(tripDay*(count+1)+index<list.size() && list.get(tripDay*(count+1)+index).getDay()==0) {
						list.get(tripDay*(count+1)+index).setDay(index+1);
						System.out.println(String.format("주소:%s, 세팅된 인덱스:%s, 세팅된 날짜:%s",list.get(tripDay*(count+1)+index).getDto().getTitle(),tripDay*(count+1)+index,list.get(tripDay*(count+1)+index).getDay()));
					}

					setDay++;
					
					count++;
					
				}
				else {
					count=0;
					//System.out.println("index"+index);
					if(index<tripDay) {
						index++;
					}

					continue;
					
					
				}//////////
			//System.out.println(setDay);
			//System.out.println(list.size()-1);
			if(setDay==list.size()) break;
			}////////////for attr most near place
		}
		/*


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
		 */
		PlannerDTO routeList = new PlannerDTO();
		routeList.setRoute(list);
		return routeList;
		
	}
	@GetMapping("/planner/selectone")
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
		/*
		for(AttractionDTO dto:list) {
			
			if(dto.getKakaokey()!=null) {
				String uri = "http://127.0.0.1:5000/aamu?map="+dto.getKakaokey();
				
				ResponseEntity<KakaoReview> responseEntity = 
						restTemplate.exchange(uri, HttpMethod.GET, null, KakaoReview.class);
				
				
				dto.setStar(responseEntity.getBody().getBasicInfo().getStar());
				list.add(dto);
			}
			
		}
		*/
		return list;
	}
	
	@GetMapping("/info/review")
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
	@GetMapping("/info/recentplace")
	public List<AttractionDTO> getRecentPlace(@RequestParam Map map){
		if(map.get("distance")==null) map.put("distance", 3);

		List<AttractionDTO> lists = service.getRecentPlaceAll(map);
		
		return lists;
	}
	

}
