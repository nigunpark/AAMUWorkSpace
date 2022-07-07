package com.aamu.aamurest.user.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.api.Info;
import com.aamu.aamurest.user.service.api.KakaoKey;
import com.aamu.aamurest.user.service.api.Places;
import com.aamu.aamurest.user.service.api.Places.Item;

@RestController
@PropertySource("classpath:aamu/resources/api.properties")
public class ApiController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MainService service;
	
	@Value("${apikey}")
	private String apikey;
	
	@Value("${kakaokey}")
	private String kakaokey;
	
	@CrossOrigin
	@PostMapping("/places/backupinfo")
	public List<AttractionDTO> info2(@RequestParam Map map) {
		String area = map.get("areacode").toString();
		String contentTypeId = map.get("contenttypeid").toString();

		HttpEntity httpEntity = null;
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "KakaoAK "+kakaokey);
		int affected=0;
		String uri;
		uri = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "serviceKey="+apikey+"&"
				+ "pageNo=1&numOfRows=1000&"
				+ "MobileApp=AppTest&MobileOS=ETC&arrange=B&"
				+ "contentTypeId="+contentTypeId+"&"
						+ "areaCode="+area+"&"
								+ "listYN=Y";
		if(contentTypeId.equals("15")) {
			Date current = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String currentTime = dateFormat.format(current);
			uri ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey="+apikey+"&numOfRows=500&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=B&listYN=Y&areaCode="+area+"&eventStartDate="+currentTime;
		}
		ResponseEntity<Places> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.GET,
				null,Places.class);
		
		List<Item> items =responseEntity.getBody().getResponse().getBody().getItems().getItem();
		List<AttractionDTO> list = new Vector<AttractionDTO>();
		for(Item item : items) {
			AttractionDTO dto = new AttractionDTO();
			dto.setAddr(item.getAddr1());
			dto.setAreacode(item.getAreacode());
			dto.setContentid(item.getContentid());
			dto.setMapx(item.getMapx());
			dto.setMapy(item.getMapy());
			dto.setTitle(item.getTitle());
			dto.setContenttypeid(item.getContenttypeid());
			dto.setBigimage(item.getFirstimage());
			dto.setSmallimage(item.getFirstimage2());
			dto.setSigungucode(item.getSigungucode());
			if(contentTypeId.equals("15")) {
				String start = String.valueOf(item.getEventstartdate());
				String end = String.valueOf(item.getEventenddate());

				dto.setTel(item.getTel());
				dto.setEventstart(start);
				dto.setEventend(end);
			}
			if(service.checkPlace(dto.getContentid())==1) {
				continue;
			}
			System.out.println(dto.getContentid());
			uri = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?"
					+ "serviceKey="+apikey
					+ "&numOfRows=1&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+item.getContentid()+"&contentTypeId="+item.getContenttypeid()+"&_type=json";
			
			ResponseEntity<Info> responseEntity2 = 
					restTemplate.exchange(uri, HttpMethod.GET,
							null,Info.class);
			String charge =null;
			switch (contentTypeId) {
			case "12":
				dto.setResttime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getRestdate());
				dto.setPlaytime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getUsetime());
				dto.setTel(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getInfocenter());
				dto.setPark(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getParking());
				break;
			case "15":
				if(dto.getTel()==null) {
					
				}
				dto.setEventtime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getPlaytime());
				dto.setCharge(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getUsetimefestival());
				charge = dto.getCharge();
				break;
			case "32":
				dto.setTel(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getReservationlodging());
				dto.setCheckin(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getCheckintime());
				dto.setCheckout(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getCheckouttime());
				dto.setUrl(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getReservationurl());
				break;
			case "39":
				
				dto.setTel(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getReservationfood());
				if(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getReservationfood()==null) {
					dto.setTel(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getInfocenter());
				}
				dto.setPlaytime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getOpentimefood());
				dto.setResttime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getRestdatefood());
				dto.setMenu(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getTreatmenu());
				dto.setPark(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getParkingfood());
				break;
			case "28":
				dto.setTel(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getInfocenterleports());
				dto.setPark(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getParkingleports());
				dto.setPlaytime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getUsetimeleports());
				dto.setResttime(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getRestdateleports());
				break;
			}
			
			
			String title = dto.getTitle();
			httpEntity = new HttpEntity(header);
			if(title!=null) {
				if(title.contains("(")) {
					title = dto.getTitle().split("\\(")[0].trim();
				}
				else if(title.contains("[")) {
					title = dto.getTitle().split("\\[")[0].trim();
				}
				else if(title.contains("{")) {
					title = dto.getTitle().split("\\{")[0].trim();
				}
			}
			String id=null;
			uri ="https://dapi.kakao.com/v2/local/search/keyword.json?"
					+ "y="+dto.getMapy()+"&x="+dto.getMapx()+"&radius=20000&query="+dto.getAddr();
			
			ResponseEntity<KakaoKey> responseEntity3 = 
					restTemplate.exchange(uri, HttpMethod.GET,
							httpEntity,KakaoKey.class);
			if(responseEntity3.getBody().getDocuments().size()==0 && title!=null) {
				uri ="https://dapi.kakao.com/v2/local/search/keyword.json?"
						+ "y="+dto.getMapy()+"&x="+dto.getMapx()+"&radius=20000&query="+title;
				responseEntity3 = restTemplate.exchange(uri, HttpMethod.GET,
								httpEntity,KakaoKey.class);
				
			}
			
			if(responseEntity3.getBody().getDocuments().size()!=0) {
			 id=  responseEntity3.getBody().getDocuments().get(0).getId();
			}
			
			dto.setKakaokey(id);
			/*
			uri="https://place.map.kakao.com/commentlist/v/"+dto.getKakaokey();
			ResponseEntity<Review> responseEntity4 = 
					restTemplate.exchange(uri, HttpMethod.GET,
							null,Review.class);
			if(responseEntity4.getBody().getComment()!=null) {
				List<com.ammu.rest.ammu.service.review.List> reviewLists = responseEntity4.getBody().getComment().getList();
				List<ReviewDTO> rDtoList = new Vector<ReviewDTO>();
				for(com.ammu.rest.ammu.service.review.List reviewList:reviewLists) {
					ReviewDTO rdto = new ReviewDTO();
					
					rdto.setReviewId(reviewList.getUsername());
					rdto.setReviewComment(reviewList.getContents());
					rdto.setPoint(reviewList.getPoint());
					
					rDtoList.add(rdto);
				}
			
			dto.setReview(rDtoList);
			}
			*/
			uri = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?serviceKey="+apikey+"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+dto.getContentid()+"&defaultYN=Y&overviewYN=Y&_type=json";
			
			responseEntity2 = 
					restTemplate.exchange(uri, HttpMethod.GET,
							null,Info.class);
			if(responseEntity2.getBody().getResponse().getBody()!=null) {
				
				
				dto.setUrl(responseEntity2.getBody().getResponse().getBody().getItems().getItem().getHomepage());
				
				
			}
			list.add(dto);
			if(dto.getAddr()!=null) {
				service.placeInsert(dto);
				switch(contentTypeId) {
				case "12":
				case "28":
					service.infoInsert(dto);
					break;
				case "15":
					service.eventInsert(dto);
					break;
				case "32":
					service.hotelInsert(dto);
					break;
				case "39":
					service.infoInsert(dto);
					service.dinerInsert(dto);
					break;
				}
			
			affected++;
			}

		}
		
	
		Map resultMap = new HashMap();
		resultMap.put("result", affected+"count success");


		return list;
	}

	@PutMapping("/places/edit")
	public List<AttractionDTO> search(@RequestBody Map map){
		System.out.println(map.get("searchWord"));
		List<AttractionDTO> lists =service.searchOnePlace(map);
		
		for(AttractionDTO dto: lists) {
			if(!(dto.getUrl().contains("http"))) {
				String uri = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?serviceKey="+apikey+"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+dto.getContentid()+"&defaultYN=Y&overviewYN=Y&_type=json";
				
				ResponseEntity<Info> responseEntity = restTemplate.exchange(uri,HttpMethod.GET,null,Info.class);
				
				dto.setUrl(responseEntity.getBody().getResponse().getBody().getItems().getItem().getHomepage());
				
				service.updateUrl(dto);
			}
			
		}
		
		return lists;
	}
	@PutMapping("/data/update")
	public int searchAll(@RequestBody Map map){
		int affected = 0; 
		System.out.println(map.get("searchword"));
		System.out.println(map.get("searchcolumn"));
		List<AttractionDTO> lists =service.searchOnePlace(map);

		
		for(AttractionDTO dto: lists) {
			
			/*
			String url = dto.getUrl();
			String resultUrl =null;
			if(url!=null) {
				System.out.println(url);
				if(url.contains("\"")) {
					dto.setUrl(url.split("\"")[1]);
					resultUrl = dto.getUrl();
					if(!resultUrl.contains("http")) {
						if(resultUrl.contains("href=\"")) {
							resultUrl = url.split("href=\"")[1];
							dto.setUrl(resultUrl.split("\"")[0]);
						}
						else {
							resultUrl = url.split("href=")[1];
							dto.setUrl(resultUrl.split("\"")[0]);
							
						}
						
						resultUrl = dto.getUrl();
					}
				}
				else {
					resultUrl = url;
					dto.setUrl(resultUrl);
				}
				
			}
			service.updateOneData(dto);
			affected++;
			*/
			String checkin = dto.getCheckin();
			String checkout =dto.getCheckout();
			if(checkin!=null) {
				if(checkin.contains("<br />")) {
					checkin.replace("<br />", "\r\n");
				}
				else if(checkin.contains("<br/>")){
					checkin.replace("<br/>", "\r\n");
				}
				else if(checkin.contains("<br>")){
					checkin.replace("<br>", "\r\n");
				}
				dto.setCheckin(checkin);
			}
			if(checkout!=null) {
				if(checkout.contains("<br />")) {
					checkout.replace("<br />", "\r\n");
				}
				else if(checkout.contains("<br/>")){
					checkout.replace("<br/>", "\r\n");
				}
				else if(checkout.contains("<br>")){
					checkout.replace("<br>", "\r\n");
				}
				dto.setCheckout(checkout);
			}
			service.updateUrl(dto);
		}
		
		
		return affected;
	}
}
