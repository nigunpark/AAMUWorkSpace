package com.aamu.aamurest.user.web;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.swing.event.ListSelectionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.RouteDTO;
import com.aamu.aamurest.user.service.api.KakaoKey;
import com.aamu.aamurest.user.service.api.KakaoKey.Document;
import com.aamu.aamurest.user.service.api.KakaoReview;
import com.aamu.aamurest.user.serviceimpl.MainServiceImpl;
import com.aamu.aamurest.util.FileUploadUtil;
import com.aamu.aamurest.util.UserUtil;

@RestController
@CrossOrigin("*")
@PropertySource("classpath:aamu/resources/api.properties")
public class MainController {

	@Value("${kakaokey}")
	private String kakaokey;
	
	private final String serverip = "http://192.168.0.22";

	@Autowired
	private MainService service;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BBSService bbsService;

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
	public PlannerDTO plannerData(@RequestBody PlannerDTO dto,HttpServletRequest req) {
		List<RouteDTO> list = dto.getRoute();
		int tripDay = list.get(0).getDay();
		int setDay =0;
		Map<String,Double> map=new HashMap<>();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "KakaoAK "+kakaokey);
		HttpEntity httpEntity = new HttpEntity<>(header);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getContentid());
			if(list.get(i).getContentid()!=0) {
				int contentid = list.get(i).getContentid();
				AttractionDTO placeInfo = service.selectOnePlace(contentid,req);
				list.get(i).setDto(placeInfo);
			}
			if(list.get(i).getDay()!=0) {
				int hotelDay = list.get(i).getDay();
				Collections.swap(list, hotelDay-1, i);
				if(tripDay<hotelDay) tripDay =hotelDay;

			}
		}///////////////list index change
		/////////////////////////
		list.get(tripDay-1).setContentid(list.get(tripDay-2).getContentid());
		list.get(tripDay-1).setContenttypeid(list.get(tripDay-2).getContenttypeid());
		for(RouteDTO route:list) {
			int contentid = route.getContentid();
			AttractionDTO placeInfo = service.selectOnePlace(contentid,req);
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
						//맛집 안겹치게하기
						if(list.get(count*tripDay+index).getContenttypeid()==39) {
							if(list.get(k).getContenttypeid()!=39) {
								map.put("secondx", list.get(k).getDto().getMapx());
								map.put("secondy", list.get(k).getDto().getMapy());
							}
						}
						else {
							map.put("secondx", list.get(k).getDto().getMapx());
							map.put("secondy", list.get(k).getDto().getMapy());
						}

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

	@GetMapping("/planner/selectone")
	public PlannerDTO selectPlannerOne(@RequestParam int rbn,HttpServletRequest req) {

		PlannerDTO dto = service.selectPlannerOne(rbn,req);

		return dto;
	}
	@GetMapping("/data/mvtm")
	public Map dataMvtm(@RequestParam Map map) {
		
		
		return map;
	}

	@GetMapping("/planner/selectonemap")
	public PlannerDTO selectMapPlannerOne(@RequestParam int rbn,HttpServletRequest req){

		Map<String,List<RouteDTO>> map = new TreeMap<>();
		PlannerDTO dto = service.selectPlannerOne(rbn,req);

		List<RouteDTO> routes =  dto.getRoute();
		int max= 0 ;
		for(RouteDTO route : routes) {
			if(max<route.getDay())
				max=route.getDay();
		}
		for(int i=1;i<=max;i++)
			map.put("day"+i, new Vector<>());


		for(RouteDTO route : routes)
			map.get("day"+route.getDay()).add(route);



		dto.setRouteMap(map);
		dto.setRoute(null);
		return dto;
	}
	@GetMapping("/planner/selectList")
	public List<PlannerDTO> selectPlannerList(HttpServletRequest req){
		
		List<PlannerDTO> list = service.getPlannerList(req.getAttribute("username").toString());
		return list;
	}
	@PostMapping("/planner/change")
	public PlannerDTO plannerChange(@RequestBody PlannerDTO dto) {
		Map<String,List<RouteDTO>> routeMap = new TreeMap<>();

		List<RouteDTO> routes =  dto.getRoute();
		int max= 0 ;
		for(RouteDTO route : routes) {
			if(max<route.getDay())
				max=route.getDay();
		}
		for(int i=1;i<=max;i++)
			routeMap.put("day"+i, new Vector<>());


		for(RouteDTO route : routes)
			routeMap.get("day"+route.getDay()).add(route);

		dto.setRouteMap(routeMap);

		int day = routeMap.size();
		long mtime=0;
		Map<String,Double> map = new HashMap<>();
		for(int i=1;i<=day;i++) {
			List<RouteDTO> list = dto.getRouteMap().get("day"+i);
			int place = list.size();
			for(int k=0;k<place;k++) {
				/*
				if(i==1 && list.size()<place) list.add(list.get(0));
				if(i==1 && k==place-1) {
					if(list.get(k).getContentid()!=list.get(0).getContentid()) {
						RouteDTO route = list.get(k);
						route = list.get(0);
					}
				}
				else if(i!=1 && i!=day) {
					int minus = i-1;
					(List)dto.getRouteMap().get("day"+minus)
				}
				else if(i==day)
				*/
				if(k>0) {
					map.put("firstx", list.get(k-1).getDto().getMapx());
					map.put("firsty", list.get(k-1).getDto().getMapy());
					map.put("secondx",list.get(k).getDto().getMapx());
					map.put("secondy",list.get(k).getDto().getMapy());

					mtime = (long)(service.getRecentPlaceOne(map)*1000*60*1.5);
					list.get(k).setMtime(mtime);
				}
			}
		}

		return dto;
	}
	@GetMapping("/info/oneplace")
	public AttractionDTO attractionOne(@RequestParam Map map,HttpServletRequest req) {
		AttractionDTO dto = new AttractionDTO();
		switch(map.get("contenttypeid").toString()) {
			case "12":
			case "28":
				map.put("selecttable", "placesinfo");
				dto=service.selectPlace(map, req);
				break;
			case "15":
				map.put("selecttable", "eventinfo");
				dto=service.selectPlace(map, req);
				break;
			case "32":
				map.put("selecttable", "hotelinfo");
				dto=service.selectPlace(map, req);
				break;
			case "39":
				map.put("selecttable", "dinerinfo");
				dto=service.selectPlace(map, req);
				break;
		}
		return dto;
	}
	@GetMapping("/info/places")
	public List<AttractionDTO> attractionList(@RequestParam Map map,HttpServletRequest req){

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
			list = service.selectAttrSigungu(map,req);
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
			list = service.selectPlacesList(map,req);
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

			String uri = serverip+":5020/review?map="+kakaoKey;

			ResponseEntity<KakaoReview> responseEntity =
					restTemplate.exchange(uri, HttpMethod.GET, null, KakaoReview.class);
			kakaReview = responseEntity.getBody();
		}
		return kakaReview;

	}

	@GetMapping("/info/search")
	public List<AttractionDTO> search(@RequestParam Map map,HttpServletRequest req){
		
		List<AttractionDTO> lists =service.searchTwoPlace(map,req);

		return lists;
	}
	@GetMapping("/info/recentplace")
	public List<AttractionDTO> getRecentPlace(@RequestParam Map map,HttpServletRequest req){
		if(map.get("distance")==null) map.put("distance", 3);

		List<AttractionDTO> lists = service.getRecentPlaceAll(map,req);

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
		+"&radius="+radius+"&category_group_code="+map.get("category")+"&page="+1+"&sort=distance";

		ResponseEntity<KakaoKey> responseEntity =
				restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
		List<Document> listDocument =  responseEntity.getBody().getDocuments();
		while(true) {
			if(listDocument.size()<10) {
				radius +=1000;
				System.out.println(radius);
				uri="https://dapi.kakao.com/v2/local/search/category.json?y="+map.get("placey")
				+"&x="+map.get("placex")
				+"&radius="+radius+"&category_group_code="+map.get("category")+"&page="+page+"&sort=distance";

				 responseEntity =
						restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
				 listDocument =  responseEntity.getBody().getDocuments();
				 if(listDocument.size()<5)continue;
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
					+"&radius="+radius+"&category_group_code="+map.get("category")+"&page="+page+"&sort=distance";

					 responseEntity =
							restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoKey.class);
					 listDocument =  responseEntity.getBody().getDocuments();
				}

			}
		
			if(responseEntity.getBody().getMeta().getIsEnd() ||list.size()>30) break;
		}


		return list;
	}
	@GetMapping("/main/mainelement")
	public Map<String,List> mainElement(@RequestParam Map map,HttpServletRequest req){
		System.out.println("메인페이지 아이디:"+map);
		//Map<String,Map<String,List>> basicMap = new HashMap<>();
		Map<String,List> mapElement = new HashMap<>();
		List<BBSDTO> bbsList = new Vector<>();
		List<AttractionDTO> list = new Vector<>();
		if(map.get("id")!=null) {			
			MultiValueMap<String,Object> requestBody = new LinkedMultiValueMap<>();
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", "application/json");
			requestBody.add("id", map.get("id").toString());
			requestBody.add("routebbs", service.selectAllBbsRate());
			requestBody.add("ratereview", service.selectAllReview());
			HttpEntity httpEntity = new HttpEntity<>(requestBody,header);
			String uri = serverip+":5020/recommend";
			ResponseEntity<Map> responseEntity =
					restTemplate.exchange(uri, HttpMethod.POST,httpEntity, Map.class);
			System.out.println(responseEntity.getBody());
			List<Map> rbnList = (List)responseEntity.getBody().get("rbns");
			int rank = 1;
			for(Map rbnMap : rbnList) {
				BBSDTO dto = service.getRouteBBS(Integer.parseInt(rbnMap.get("rbn").toString()),req);

				for(RouteDTO routeDTO:dto.getRouteList()) {
					AttractionDTO placeDto = service.selectOnePlace(routeDTO.getContentid(), req);
					if(placeDto.getContenttypeid()!=32 && list.size()<6 && rank==1) {
						System.out.println("뭐가 들어올까요"+dto.getContent());
						list.add(placeDto);
						rank++;
					}
				}
					

				rank=1;
				if(bbsList.size()<4)bbsList.add(dto);
			}
			
		}
		else {
			list = service.getTopAttr();
			bbsList =  service.selectTopBBS(req);
		}
		mapElement.put("placeInfo", list);
		mapElement.put("bbsInfo", bbsList);
		//basicMap.put("placesInfo", mapElement);

		return mapElement;

	}
	@PostMapping("/img/upload")
	public Map imgUpload(@RequestParam Map map,@RequestParam MultipartFile files,HttpServletRequest req) throws IllegalStateException, IOException {
		int affected = 0;
		System.out.println(map.get("contentid"));
		System.out.println(files);
		String path = req.getSession().getServletContext().getRealPath("/resources/hotelImage");
		String filename = FileUploadUtil.oneFile(files, path);
		map.put("smallimage", filename);
		affected = service.updateImage(map);
		map.put("smallimage", FileUploadUtil.requestOneFile(filename, "/resources/hotelImage", req));
		return map;
	}
	@PostMapping("/main/chatbot")
	public Map mainChatbot(@RequestBody Map map,HttpServletRequest req) {
		System.out.println(map);
		String uri= serverip+":5020/message";
		MultiValueMap<String,Object> requestBody = new LinkedMultiValueMap<>();
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		requestBody.add("id", map.get("id").toString());
		requestBody.add("message", map.get("message").toString());
		HttpEntity httpEntity = new HttpEntity<>(requestBody,header);

		ResponseEntity<Map> responseEntity =
				restTemplate.exchange(uri, HttpMethod.POST,httpEntity, Map.class);
		System.out.println(responseEntity.getBody());
		Map returnMap = responseEntity.getBody();
		String message = returnMap.get("message").toString();
		returnMap.put("bool", true);
		returnMap.put("rbn", null);
		returnMap.put("bbslist", null);
		returnMap.put("dtolist", null);
		returnMap.put("route", null);
		String rbn = null;
		List<BBSDTO> bbsList = new Vector<>();
		List<ReviewDTO> reviewList = new Vector<>();
		if(message.contains("recommendRoute")) {
			/*
			message = message.split("searchRoute")[0].trim();
			System.out.println("마지막 응답 메시지:"+message);
			rbn = service.searchPlanner(message);
			if(rbn == null) message = "죄송합니다 알맞은 플래너가 없습니다.";
			else {
				returnMap.put("route", message+" (CLICK)");
				returnMap.put("rbn",rbn);
				int rbnInt = Integer.parseInt(rbn);
				BBSDTO dto = bbsService.bbsSelectOne(rbnInt);
				returnMap.put("planner", dto);
				message = "AAMU에서 추천하는 여행 플래너!";
			}
				
			
			returnMap.put("message", message);
			*/
			requestBody.add("routebbs", service.selectAllBbsRate());
			requestBody.add("ratereview", service.selectAllReview());
			uri=serverip+":5020/recommend";
			responseEntity =
					restTemplate.exchange(uri, HttpMethod.POST,httpEntity, Map.class);
			message = "AAMU에서 추천하는 여행 플래너!";
			List<Map> list = (List)responseEntity.getBody().get("rbns");
			System.out.println(list.toString());

			for(Map planner:list) {
				
				BBSDTO bbsMap =bbsService.bbsSelectOne((Integer.parseInt(planner.get("rbn").toString())));
				if(bbsMap.getReviewList()==null)bbsMap.setReviewList(reviewList);
				bbsList.add(bbsMap);
			}
			returnMap.put("message", message);
			returnMap.put("bbslist", bbsList);
			return returnMap;
		}
		else if(message.contains("searchPlace")){
			message = message.split("searchPlace")[0].trim();
			String area = message.split("\\s")[0];
			String contenttype = message.split("\\s")[1];
			System.out.println("지역:"+area);
			System.out.println("장소:"+contenttype);
			returnMap.put("message", message);
			Map codeMap = MainServiceImpl.switchArea(area, contenttype);
			System.out.println(codeMap);
			codeMap.put("rownum", 1);
			List<String> contentids = service.searchMostRoute(codeMap);
			List<Map> dtoList = new Vector<>();
			if(contentids!=null) {
				for(String contentid:contentids) {
					Map contentMap = new HashMap<>();
					AttractionDTO dto = service.selectOnePlace(Integer.parseInt(contentid),req);
					contentMap.put("title",dto.getTitle());
					contentMap.put("kakaokey", dto.getKakaokey());
					dtoList.add(contentMap);
				}
				returnMap.put("dtolist", dtoList);
				returnMap.put("message","현재 AAMU에서 추천하는 "+contenttype+" 입니다");

			}
			else {
				returnMap.put("message","죄송합니다 현재 추천할만한 장소가 업습니다.");
			}
			
		}
		else if(message.contains("searchRoute")) {
			message = message.split("searchRoute")[0].trim();
			System.out.println("마지막 응답 메시지:"+message);
			map.put("searchword", message);
			List<BBSDTO> searchbbsList =  service.searchBbsRate(map);
			System.out.println("챗봇가기전 리스트:"+searchbbsList.toString());
			if(!searchbbsList.isEmpty()) {
				requestBody.add("routebbs", searchbbsList);
				for(BBSDTO bbsDTO:searchbbsList) {
					map.put("rbn", bbsDTO.getRbn());
					requestBody.add("ratereview", service.searchBbsReview(map));
				}
				uri=serverip+":5020/recommend";
				responseEntity =
						restTemplate.exchange(uri, HttpMethod.POST,httpEntity, Map.class);
				System.out.println(responseEntity.getBody().toString());
				List<Map> list = (List)responseEntity.getBody().get("rbns");
				System.out.println(list.toString());
				if(!list.isEmpty()) {
					for(Map planner:list) {
						
						BBSDTO bbsMap =bbsService.bbsSelectOne((Integer.parseInt(planner.get("rbn").toString())));
						if(bbsMap.getReviewList()==null)bbsMap.setReviewList(reviewList);
						bbsList.add(bbsMap);
					}
					System.out.println("챗봇 리스트:"+bbsList.toString());
					returnMap.put("route", message+" (CLICK)");
					returnMap.put("bbslist", bbsList);
					message = message+"로 가는 AAMU여행 추천 플래너!";
				}
				else message = "죄송합니다 알맞은 플래너가 없습니다.";
				
			}
			
			else message = "죄송합니다 알맞은 플래너가 없습니다.";
			returnMap.put("message", message);
		}
		return returnMap;
	}
	@GetMapping("/main/area")
	public List<Map> areaTopAttr(Map map){
		return service.getTopPlaceInArea(map);
	}

}
