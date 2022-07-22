package com.aamu.aamurest.user.web;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;
import com.aamu.aamurest.user.service.api.KakaoKey;
import com.aamu.aamurest.user.service.api.KakaoKey.Document;
import com.aamu.aamurest.user.service.api.KakaoReview;
import com.aamu.aamurest.user.service.api.KakaoReview.CommentInfo;

@RestController
@CrossOrigin("*")
@PropertySource("classpath:aamu/resources/api.properties")
public class MainController {
	
	@Value("${kakaokey}")
	private String kakaokey;
	
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
		Map<String,Double> map=new HashMap<>();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "KakaoAK "+kakaokey);
		HttpEntity httpEntity = new HttpEntity<>(header);
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
		int result = (int)Math.ceil(((double)list.size()-tripDay)/tripDay);
		int index=0;
		int count = 0;
		long mtime=0;
		/////////////////////////////
		while(true){
			
			if(count<result) {
				/*
				double standardx = list.get(count*tripDay+index).getDto().getMapx();
				double standardy = list.get(count*tripDay+index).getDto().getMapy();
				*/
				map.put("firstx", list.get(count*tripDay+index).getDto().getMapx());
				map.put("firsty", list.get(count*tripDay+index).getDto().getMapy());
				//System.out.println("기준장소:"+list.get(count*tripDay+index).getDto().getTitle());
				double low = Integer.MAX_VALUE;
				//double attrx =0;
				//double attry =0;
				double resultxy=0;
				for(int k=tripDay;k<list.size();k++) {
					
					if(list.get(k).getDay()==0) {
						
						//System.out.println(k);
						/*
						attrx = list.get(k).getDto().getMapx();
						attry = list.get(k).getDto().getMapy();
						resultxy = Math.sqrt(Math.pow(Math.abs(standardx-attrx),2)+Math.pow(Math.abs(standardy-attry),2));
						*/
						
						map.put("secondx", list.get(k).getDto().getMapx());
						map.put("secondy", list.get(k).getDto().getMapy());
						
						resultxy = service.getRecentPlaceOne(map);
						if(low>resultxy && tripDay*(count+1)+index<list.size()) {
							
							low=resultxy;
							
							
							//System.out.println("low:"+low);
							//System.out.println(String.format("swap할 인덱스 tripday:%s,index:%s,주소:%s",tripDay*(count+1)+index,k,list.get(k).getDto().getTitle()));
							
						}
			
					}
					if(k==list.size()-1) {
						for(int j=tripDay;j<list.size();j++) {
							//attrx = list.get(j).getDto().getMapx();
							//attry = list.get(j).getDto().getMapy();
							//resultxy = Math.sqrt(Math.pow(Math.abs(standardx-attrx),2)+Math.pow(Math.abs(standardy-attry),2));
							if(j!=count*tripDay+index) {
								map.put("secondx", list.get(j).getDto().getMapx());
								map.put("secondy", list.get(j).getDto().getMapy());
								resultxy = service.getRecentPlaceOne(map);
								
								if(low==resultxy) {
									Collections.swap(list,j,tripDay*(count+1)+index);
									
									break;} 
							}
						}
					}
					
				}
				if(tripDay*(count+1)+index<list.size() && list.get(tripDay*(count+1)+index).getDay()==0) {
					list.get(tripDay*(count+1)+index).setDay(index+1);
					/*
					String uri = "http://192.168.0.150:5000/mvtm?firstx="+list.get(count*tripDay+index).getDto().getMapx()+"&firsty="
							+list.get(count*tripDay+index).getDto().getMapy()+"&secondx="
							+list.get(tripDay*(count+1)+index).getDto().getMapx()+"&secondy="
							+list.get(tripDay*(count+1)+index).getDto().getMapy();
							*/
					/*
					ResponseEntity<Map> responseEntity = 
							restTemplate.exchange(uri, HttpMethod.GET, null, Map.class);
					long mtime = Long.parseLong(responseEntity.getBody().get("MVTM").toString())*1000;
					*/
					/*
					String uri = "https://apis-navi.kakaomobility.com/v1/directions?origin="+list.get(count*tripDay+index).getDto().getMapx()+","+list.get(count*tripDay+index).getDto().getMapy()+"&"
							+ "destination="+list.get(tripDay*(count+1)+index).getDto().getMapx()+","+list.get(tripDay*(count+1)+index).getDto().getMapy()+"&"
							+ "waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false";
					
					ResponseEntity<Map> responseEntity = 
							restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Map.class);
					System.out.println(responseEntity.getBody());
					long mtime=0;
					if(((Map)((List)responseEntity.getBody().get("routes")).get(0)).get("result_code").toString().equals("0"))
						mtime = Long.parseLong(((Map)((List)((Map)((List)responseEntity.getBody().get("routes")).get(0)).get("sections")).get(0)).get("duration").toString())*1000;
					
					list.get(tripDay*(count+1)+index).setMtime(mtime);
					*/
					if(count==0 && index!=0) {
						map.put("firstx", list.get(count*tripDay+index-1).getDto().getMapx());
						map.put("firsty", list.get(count*tripDay+index-1).getDto().getMapy());
						map.put("secondx",list.get(tripDay*(count+1)+index).getDto().getMapx());
						map.put("secondy",list.get(tripDay*(count+1)+index).getDto().getMapy());
						
						mtime = (long)(service.getRecentPlaceOne(map)*1000*60*1.5);
						list.get(tripDay*(count+1)+index).setMtime(mtime);
					}
					
					else if(count==result-1) {
						map.put("firstx",list.get(tripDay*(count+1)+index).getDto().getMapx());
						map.put("firsty",list.get(tripDay*(count+1)+index).getDto().getMapy());
						map.put("secondx", list.get(index).getDto().getMapx());
						map.put("secondy", list.get(index).getDto().getMapy());
						
						mtime = (long)(service.getRecentPlaceOne(map)*1000*60*1.5);
						
						list.get(index).setMtime(mtime);	
					}
					else{
						map.put("firstx", list.get(count*tripDay+index).getDto().getMapx());
						map.put("firsty", list.get(count*tripDay+index).getDto().getMapy());
						map.put("secondx",list.get(tripDay*(count+1)+index).getDto().getMapx());
						map.put("secondy",list.get(tripDay*(count+1)+index).getDto().getMapy());
	
						mtime = (long)(service.getRecentPlaceOne(map)*1000*60*1.5);
						
						list.get(tripDay*(count+1)+index).setMtime(mtime);
					}

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
	@PostMapping("/planner/mapdata")
	public PlannerDTO plannerMapData(@RequestBody PlannerDTO dto) {
	
		return dto;
	}
	@GetMapping("/planner/selectone")
	public PlannerDTO selectPlannerOne(@RequestParam int rbn) {
		
		PlannerDTO dto = service.selectPlannerOne(rbn);
		
		return dto;
	}

	@GetMapping("/planner/selectonemap")
	public PlannerDTO selectMapPlannerOne(@RequestParam int rbn){

		Map<String,List<RouteDTO>> map = new TreeMap<>();
		PlannerDTO dto = service.selectPlannerOne(rbn);

		List<RouteDTO> routes =  dto.getRoute();
		int max= 0 ;
		for(RouteDTO route : routes) {
			if(max<route.getDay()) 
				max=route.getDay();	
		}
		for(int i=1;i<=max;i++) 
			map.put("day"+i, new Vector<>());
			
		
		for(RouteDTO route : routes) 
			((List<RouteDTO>)map.get("day"+route.getDay())).add(route);		
		
		

		dto.setRouteMap(map);
		dto.setRoute(null);
		return dto;
	}
	@GetMapping("/planner/selectList")
	public List<PlannerDTO> selectPlannerList(@RequestParam String id){
		
		List<PlannerDTO> list = service.getPlannerList(id);
		
		return list;
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
			System.out.println(dto.getKakaokey());
			if(dto.getKakaokey()!=null) {
				String uri = "http://192.168.0.19:5000/review?map="+dto.getKakaokey();
				
				ResponseEntity<KakaoReview> responseEntity = 
						restTemplate.exchange(uri, HttpMethod.GET, null, KakaoReview.class);
				System.out.println(responseEntity.getBody().getBasicInfo().getStar());
				dto.setStar(responseEntity.getBody().getBasicInfo().getStar());
				
			
			}
			
		}
		*/
		return list;
	}
	
	@GetMapping("/info/review")
	public KakaoReview getReview(@RequestParam String kakaoKey) {
		KakaoReview kakaReview = new KakaoReview();
		if(kakaoKey!=null) {
			
			String uri = "http://192.168.0.19:5000/review?map="+kakaoKey;
			
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
	@GetMapping("/info/recentdiner")
	public List<AttractionDTO> getRecentDiner(@RequestParam Map map){
		int radius = 1000;
		int page = 1;
		List<AttractionDTO> list = new Vector<>();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "KakaoAK "+kakaokey);
		HttpEntity httpEntity = new HttpEntity(header);
		
		String uri="https://dapi.kakao.com/v2/local/search/category.json?y="+map.get("placey")
		+"&x="+map.get("placex")
		+"&radius="+radius+"&category_group_code=FD6&page="+1+"&sort=distance";

		ResponseEntity<KakaoKey> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
		List<Document> listDocument =  responseEntity.getBody().getDocuments();
		while(true) {
			if(listDocument.size()<10) {
				radius +=1000;
				System.out.println(radius);
				uri="https://dapi.kakao.com/v2/local/search/category.json?y="+map.get("placey")
				+"&x="+map.get("placex")
				+"&radius="+radius+"&category_group_code=FD6&page="+page+"&sort=distance";

				 responseEntity = 
						restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
				 listDocument =  responseEntity.getBody().getDocuments();
				 if(listDocument.size()<10)continue;
			}
			for(Document document:listDocument) {
				
				AttractionDTO dto = new AttractionDTO();
				dto.setAddr(document.getRoadAddressName());
				dto.setContenttypeid(39);
				dto.setUrl(document.getPlaceUrl());
				dto.setTel(document.getPhone());
				dto.setTitle(document.getPlaceName());
				dto.setMenu(document.getCategoryName());
				dto.setMapx(Double.parseDouble(document.getX()));
				dto.setMapy(Double.parseDouble(document.getY()));
				
				list.add(dto);

				if(!responseEntity.getBody().getMeta().getIsEnd()&&list.size()==15*page) {

					page++;
					uri="https://dapi.kakao.com/v2/local/search/category.json?y="+map.get("placey")
					+"&x="+map.get("placex")
					+"&radius="+radius+"&category_group_code=FD6&page="+page+"&sort=distance";

					 responseEntity = 
							restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
					 listDocument =  responseEntity.getBody().getDocuments();
				}
				
			}
			if(responseEntity.getBody().getMeta().getIsEnd() ||list.size()>50) break;
		}
		
		
		return list;
	}
	@GetMapping("/main/mainelement")
	public Map<String,Map<String,List>> mainElement(){
		
		Map<String,Map<String,List>> map = new HashMap<>();
		Map<String,List> mapElement = new HashMap<>();
		List<AttractionDTO> list = service.selectMainPlaceList();
		mapElement.put("places", list);
		
		return map;
		
	}
	

}
