package com.aamu.aamurest.user.serviceimpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.RouteDTO;
import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.util.FileUploadUtil;
import com.aamu.aamurest.util.UserUtil;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainDAO dao;
	
	@Autowired
	private BBSDAO bbsDao;
	@Autowired
	private TransactionTemplate transactionTemplate;

///////////////////////////////////////////////////insert place impl
	@Override
	public int placeInsert(AttractionDTO dto) {
		String url = dto.getUrl();
		String resultUrl =null;
		if(url!=null) {
			if(url.contains("\"")) {
				dto.setUrl(url.split("\"")[1]);

				resultUrl = dto.getUrl();
				if(!resultUrl.contains("http")) {
					if(resultUrl.contains("href=\"")) {
						resultUrl = url.split("href=\"")[1];
						dto.setUrl(resultUrl.split("\"")[0]);
					}
					else if(url.contains("href=")) {
						resultUrl = url.split("href=")[1];
						dto.setUrl(resultUrl.split("\"")[0]);

					}
					else {
						dto.setUrl(url.split("\"")[2]);
					}

					resultUrl = dto.getUrl();
				}
			}
			else {
				resultUrl = url;
				dto.setUrl(resultUrl);
			}

		}
		if(dto.getMapx()>999 || dto.getMapy()>999) {
			dto.setMapx(0);
			dto.setMapy(0);
		}
		String tel = dto.getTel();
		if(tel!=null) {
			if(tel.contains("<br />")) {
				tel = tel.replace("<br />", "\r\n");
			}
			else if(tel.contains("<br/>")){
				tel = tel.replace("<br/>", "\r\n");
			}
			else if(tel.contains("<br>")){
				tel = tel.replace("<br>", "\r\n");
			}
			dto.setTel(tel);
			if(tel.length()>100) {
				dto.setTel("");
			}
		}
		////////////////////////////////////////////////////////////////////////////
		if(dto.getContenttypeid()==12||dto.getContenttypeid()==28||dto.getContenttypeid()==39) {
			String playtime = dto.getPlaytime();
			String resttime = dto.getResttime();
			if(playtime!=null) {
				if(playtime.contains("<br />")) {
					playtime = playtime.replace("<br />", "\r\n");
				}
				else if(playtime.contains("<br/>")){
					playtime = playtime.replace("<br/>", "\r\n");
				}
				else if(playtime.contains("<br>")){
					playtime = playtime.replace("<br>", "\r\n");
				}
				dto.setPlaytime(playtime);
			}
			if(resttime!=null) {
				if(resttime.contains("<br />")) {
					resttime = resttime.replace("<br />", "\r\n");
				}
				else if(resttime.contains("<br/>")){
					resttime = resttime.replace("<br/>", "\r\n");
				}
				else if(resttime.contains("<br>")){
					resttime.replace("<br>", "\r\n");
				}
				dto.setResttime(resttime);
			}
		}///////////////////////////////////////////////////////////////////////////////////
		else if(dto.getContenttypeid()==32) {
			String checkin = dto.getCheckin();
			String checkout =dto.getCheckout();
			if(checkin!=null) {
				if(checkin.contains("<br />")) {
					checkin = checkin.replace("<br />", "\r\n");
				}
				else if(checkin.contains("<br/>")){
					checkin = checkin.replace("<br/>", "\r\n");
				}
				else if(checkin.contains("<br>")){
					checkin = checkin.replace("<br>", "\r\n");
				}
				dto.setCheckin(checkin);
			}
			if(checkout!=null) {
				if(checkout.contains("<br />")) {
					checkout = checkout.replace("<br />", "\r\n");
				}
				else if(checkout.contains("<br/>")){
					checkout = checkout.replace("<br/>", "\r\n");
				}
				else if(checkout.contains("<br>")){
					checkout = checkout.replace("<br>", "\r\n");
				}
				dto.setCheckout(checkout);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////
		else if(dto.getContenttypeid()==15) {
			/*
			String start = dto.getEventstart();
			String end = dto.getEventend();
			start = start.substring(0, 4) +"-"+start.substring(4, 6)+"-"+start.substring(6,8);
			end = end.substring(0, 4) +"-"+end.substring(4, 6)+"-"+end.substring(6,8);
			dto.setEventstart(start);
			dto.setEventend(end);
			System.out.println(dto.getEventstart());
			System.out.println(dto.getEventend());
			*/
			String eventTime = dto.getEventtime();
			if(eventTime!=null) {
				if(eventTime.contains("<br>")) {
					eventTime = eventTime.replace("<br>", "\r\n");
				}
				else if(eventTime.contains("<br />")) {
					eventTime = eventTime.replace("<br />", "\r\n");
				}
				else if(eventTime.contains("<br/>")){
					eventTime = eventTime.replace("<br/>", "\r\n");
				}
				dto.setEventtime(eventTime);
			}
			String charge = dto.getCharge();
			if(charge!=null) {
				if(charge.contains("<br>")) {
					charge = charge.replace("<br>", "\r\n");

				}
				else if(charge.contains("<br />")) {
					charge = charge.replace("<br />", "\r\n");
				}
				else if(charge.contains("<br/>")){
					charge = charge.replace("<br/>", "\r\n");
				}
				if(charge.length()>100) {
					charge = dto.getUrl();
				}
				dto.setCharge(charge);
			}
		}//////////////////////////////////////////////////////////////////////
		return dao.placeInsert(dto);
	}
	@Override
	public int plannerInsert(PlannerDTO dto) {
		int affected=0;

		affected = transactionTemplate.execute(tx->{
			int insertPlanner = dao.plannerInsert(dto);
			List<RouteDTO> routes = dto.getRoute();

			for(RouteDTO route:routes) {
				System.out.println("routeid 입니다:"+route.getContentid());
				System.out.println("routecontenttypeid 입니다:"+route.getContenttypeid());
				System.out.println("순서입니다:"+route.getOrdno());
				route.setRbn(dto.getRbn());
				dao.routeInsert(route);
			}
			return insertPlanner;

		});

		/*
		int insertPlanner = dao.plannerInsert(dto);
		List<RouteDTO> routes = dto.getRoute();

		for(RouteDTO route:routes) {
			route.setRbn(dto.getRbn());
			dao.routeInsert(route);
		}
		*/
		return affected;
	}
	@Override
	public int updatePlanner(PlannerDTO dto) {
		int affected=0;

		affected = transactionTemplate.execute(tx->{
			int updatePlanner = dao.updatePlanner(dto);
			dao.deleteRoute(dto.getRbn());
			List<RouteDTO> routes = dto.getRoute();

			for(RouteDTO route:routes) {
				route.setRbn(dto.getRbn());
				dao.routeInsert(route);
			}
			return updatePlanner;
		});
		/*
		int updatePlanner = dao.updatePlanner(dto);
		dao.deleteRoute(dto.getRbn());
		List<RouteDTO> routes = dto.getRoute();

		for(RouteDTO route:routes) {
			route.setRbn(dto.getRbn());
			dao.routeInsert(route);
		}
		*/
		return affected;
	}

///////////////////////////////////////////////////get place impl

	@Override
	public List<AttractionDTO> selectPlacesList(Map map,HttpServletRequest req) {
		List<AttractionDTO> list = dao.selectPlacesList(map);
		list = changeAttr(list, req);
		return list;
	}
	@Override
	public List<AttractionDTO> selectAttrSigungu(Map map,HttpServletRequest req) {
		
		return changeAttr(dao.selectAttrSigungu(map),req);
	}
///////////////////////////////////////////////////search place impl
	@Override
	public List<AttractionDTO> searchTwoPlace(Map map,HttpServletRequest req) {

		switch(map.get("contenttypeid").toString()) {
		case "12":
		case "28":
			map.put("searchtable", "placesinfo");
			break;
		case "15":
			map.put("searchtable", "eventinfo");
			break;
		case "32":
			map.put("searchtable", "hotelinfo");
			break;
		case "39":
			map.put("searchtable", "dinerinfo");
			break;
		}
		return changeAttr(dao.searchTwoPlace(map), req);
	}

	@Override
	public List<AttractionDTO> searchOnePlace(Map map,HttpServletRequest req) {

		return changeAttr(dao.searchOnePlace(map), req);
	}
///////////////////////////////////////////////////update place impl
	@Override
	public int updatePlaces(Map map) {
		String url = null;
		if(map.get("url")!=null) url = map.get("url").toString();
		String resultUrl =null;
		if(url!=null) {
			if(url.contains("\"")) {
				map.put("url",url.split("\"")[1]);
				resultUrl = map.get("url").toString();
				if(!resultUrl.contains("http")) {
					if(resultUrl.contains("href=\"")) {
						resultUrl = url.split("href=\"")[1];
						map.put("url",resultUrl.split("\"")[0]);
					}
					else {
						resultUrl = url.split("href=")[1];
						map.put("url",resultUrl.split("\"")[0]);

					}

					resultUrl = map.get("url").toString();
				}
			}
			else {
				resultUrl = url;
				map.put("url",resultUrl);
			}
		}
		String tel=null;
		if(map.get("tel")!=null) tel = map.get("tel").toString();
		if(tel!=null) {
			if(tel.contains("<br />")) {
				tel = tel.replace("<br />", "\r\n");
			}
			else if(tel.contains("<br/>")){
				tel = tel.replace("<br/>", "\r\n");
			}
			else if(tel.contains("<br>")){
				tel = tel.replace("<br>", "\r\n");
			}
			map.put("tel", tel);
		}
		if(map.get("playtime")!=null) {
			String playtime = map.get("playtime").toString();
			String resttime = map.get("resttime").toString();
			if(playtime!=null) {
				if(playtime.contains("<br />")) {
					playtime = playtime.replace("<br />", "\r\n");
				}
				else if(playtime.contains("<br/>")){
					playtime = playtime.replace("<br/>", "\r\n");
				}
				else if(playtime.contains("<br>")){
					playtime = playtime.replace("<br>", "\r\n");
				}
				map.put("playtime", playtime);
			}
			if(resttime!=null) {
				if(resttime.contains("<br />")) {
					resttime = resttime.replace("<br />", "\r\n");
				}
				else if(resttime.contains("<br/>")){
					resttime = resttime.replace("<br/>", "\r\n");
				}
				else if(resttime.contains("<br>")){
					resttime.replace("<br>", "\r\n");
				}
				map.put("resttime", resttime);
			}
		}
		if(map.get("eventtime")!=null) {
			String eventtime = map.get("eventtime").toString();
			String charge = map.get("charge").toString();

			if(eventtime!=null) {
				if(eventtime.contains("<br>")) {
					eventtime = eventtime.replace("<br>", "\r\n");
				}
				else if(eventtime.contains("<br />")) {
					eventtime = eventtime.replace("<br />", "\r\n");
				}
				else if(eventtime.contains("<br/>")){
					eventtime = eventtime.replace("<br/>", "\r\n");
				}
				map.put("eventtime", eventtime);
			}
			if(charge!=null) {
				if(charge.contains("<br>")) {
					charge = charge.replace("<br>", "\r\n");

				}
				else if(charge.contains("<br />")) {
					charge = charge.replace("<br />", "\r\n");
				}
				else if(charge.contains("<br/>")){
					charge = charge.replace("<br/>", "\r\n");
				}
				map.put("charge", charge);
			}
		}

		return dao.updatePlaces(map);
	}
	////////////////////////////////////selectone place
	@Override
	public AttractionDTO selectOnePlace(int contentid,HttpServletRequest req) {
		AttractionDTO dto = changeOneAttr(dao.selectOnePlace(contentid), req);	
		return dto;
	}
	@Override
	public int checkPlace(Map map) {

		return dao.checkPlace(map);
	}
	@Override
	public int updateUrl(AttractionDTO dto) {
		return dao.updateUrl(dto);
	}




	///////////////planner update
	@Override
	public int updateRoute(List<RouteDTO> routes) {

		return dao.updateRoute(routes);
	}

	@Override
	public int deletePlanner(int rbn) {
		int affected=0;
		Map map = new HashMap<>();
		affected = transactionTemplate.execute(tx->{
				map.put("rbn", rbn);
				map.put("table", "routebbsphoto");
				dao.deletePlanner(map);
				map.put("table", "ratereview");
				dao.deletePlanner(map);
				map.put("table", "routebbs");
				 dao.deletePlanner(map);
				map.put("table", "route");
				dao.deletePlanner(map);

				map.put("table", "routeboard");
				return dao.deletePlanner(map);
			});
		return affected;
	}

	@Override
	public List<AttractionDTO> getRecentPlaceAll(Map map,HttpServletRequest req) {
		List<AttractionDTO> lists = dao.getRecentPlaceAll(map);
		List<AttractionDTO> returnList = new Vector<>();
		for(int i=0;i<lists.size();i++) {
			AttractionDTO dto =lists.get(i);
			switch(dto.getContenttypeid()) {
			case 12:
			case 28:
				map.put("selecttable", "placesinfo");
				map.put("contentid", dto.getContentid());
				dto = changeOneAttr(dao.selectPlace(map), req);
				break;
			case 15:
				map.put("selecttable", "eventinfo");
				map.put("contentid", dto.getContentid());
				dto = changeOneAttr(dao.selectPlace(map), req);
				break;
			case 32:
				map.put("selecttable", "hotelinfo");
				map.put("contentid", dto.getContentid());
				dto = changeOneAttr(dao.selectPlace(map), req);
				break;
			case 39:
				map.put("selecttable", "dinerinfo");
				map.put("contentid", dto.getContentid());
				dto = changeOneAttr(dao.selectPlace(map), req);
				break;
			}
			returnList.add(dto);
		}

		return returnList;
	}
	@Override
	public double getRecentPlaceOne(Map map) {

		return dao.getRecentPlaceOne(map);
	}
	@Override
	public List<PlannerDTO> getPlannerList(String id) {
		List<PlannerDTO> list = dao.getPlannerList(id);
		List<PlannerDTO> returnList = new Vector<>();
		int index = 1;
		int day =0;
		for(PlannerDTO dto:list) {
			List<RouteDTO> routes = dao.selectRouteList(dto.getRbn());
			dto.setIsBBS(dao.countRouteBBS(dto.getRbn()));

			dto.setRoute(routes);
			for(RouteDTO route:routes) {
				route.setDto(dao.selectOnePlace(route.getContentid()));
				if(route.getDto().getContenttypeid()==12&&index==1) {
					dto.setSmallImage(route.getDto().getSmallimage());
					dto.setBigImage(route.getDto().getBigimage());
					index++;
					day = route.getDay();
				}
				if(route.getDto().getContenttypeid()==12&&day<route.getDay()) {
					index--;
				}
				route.setDto(null);
				index=1;
				day=0;
			}
			dto.setTheme(dao.getThemeFromRbn(dto.getRbn()));
			dto.setRoute(null);
			returnList.add(dto);
		}


		return returnList;
	}
	@Override
	public PlannerDTO selectPlannerOne(int rbn,HttpServletRequest req) {
		PlannerDTO dto = dao.selectPlannerOne(rbn);
		List<RouteDTO>routes =  dao.selectRouteList(rbn);
		List<RouteDTO> routes2 = new Vector<>();
		for(RouteDTO route:routes) {
			AttractionDTO aDto =  dao.selectOnePlace(route.getContentid());
			route.setDto(changeOneAttr(aDto, req));
			routes2.add(route);
		}
		dto.setTheme(dao.getThemeFromRbn(rbn));
		dto.setRoute(routes2);

		return dto;
	}
	@Override
	public List<AttractionDTO> selectMainPlaceList(HttpServletRequest req) {

		return dao.selectMainPlaceList();
	}
	@Override
	public int updateImage(Map map) {
		
		return dao.updateImage(map);
	}
	
	public List<AttractionDTO> changeAttr(List<AttractionDTO> list,HttpServletRequest req){
		List<AttractionDTO> returnList = new Vector<>();

		for(AttractionDTO dto:list) {
			String title = dto.getTitle();
			if(title!=null && dto.getSmallimage()!=null) {
				if(!(dto.getSmallimage().toString().contains("http")) && dto.getSmallimage()!=null) 
					dto.setSmallimage(FileUploadUtil.requestOneFile(dto.getSmallimage(),"/resources/hotelImage",req));
				
				if(title!=null && title.contains("[") &&!(title.split("\\[")[0].equals("")))
					dto.setTitle(title.split("\\[")[0]);
				
				if(title!=null && title.contains("(")&&!(title.split("\\(")[0].equals("")))
					dto.setTitle(title.split("\\(")[0]);
			}
			returnList.add(dto);
		}
		
		return returnList;
	}
	public AttractionDTO changeOneAttr(AttractionDTO dto,HttpServletRequest req) {
		String title = dto.getTitle();
		if(title!=null && dto.getSmallimage()!=null) {
			if(!(dto.getSmallimage().toString().contains("http")) && dto.getSmallimage()!=null) 
				dto.setSmallimage(FileUploadUtil.requestOneFile(dto.getSmallimage(),"/resources/hotelImage",req));
			
			if(title!=null && title.contains("[") &&!(title.split("\\[")[0].equals("")))
				dto.setTitle(title.split("\\[")[0]);
			
			if(title!=null && title.contains("(")&&!(title.split("\\(")[0].equals("")))
				dto.setTitle(title.split("\\(")[0]);
		}
		return dto;
		
	}
	@Override
	public String searchPlanner(String message) {
		
		return dao.searchPlanner(message);
	}

	
	public static Map switchArea(String area,String contenttype) {
		Map codeMap = new HashMap<>();
		String areacode=null;
		String contenttypeid=null;
		switch(area) {
			case "서울":
				areacode="1";
				break;
			case "인천":
				areacode="2";
				break;
			case "대전":
				areacode="3";
				break;
			case "대구":
				areacode="4";
				break;
			case "광주":
				areacode="5";
				break;
			case "부산":
				areacode="6";
				break;
			case "울산":
				areacode="7";
				break;
			case "세종":
				areacode="8";
				break;
			case "경기도":
				areacode="31";
				break;
			case "강원도":
				areacode="32";
				break;
			case "충청북도":
				areacode="33";
				break;
			case "충청남도":
				areacode="34";
				break;
			case "경상북도":
				areacode="35";
				break;
			case "경상암도":
				areacode="36";
				break;
			case "전라북도":
				areacode="37";
				break;
			case "전라남도":
				areacode="38";
				break;
			case "제주도":
				areacode="39";
				break;
			}
		switch(contenttype) {
			case"관광지":
				contenttypeid="12";
				break;
			case"호텔":
				contenttypeid="32";
				break;
			case"식당":
				contenttypeid="39";
				break;
			case"행사":
				contenttypeid="15";
				break;
			case"레져":
				contenttypeid="28";
				break;	
		}
		codeMap.put("areacode", areacode);
		codeMap.put("contenttypeid", contenttypeid);
		
		return codeMap;
		
	}
	@Override
	public List<String> searchMostRoute(Map map) {
		
		return dao.searchMostRoute(map);
	}
	@Override
	public List<String> searchTopPlaces(Map map) {
		
		return dao.searchTopPlaces(map);
	}
	@Override
	public int insertTheme(Map map) {
		
		return dao.insertTheme(map);
	}
	@Override
	public List<String> getUserTheme(Map map) {
		List<String> standTheme = dao.getUserTheme(map);
		UsersDTO dto = dao.getUserChar(map);
		LocalDate now = LocalDate.now();
		int age = now.getYear()-Integer.parseInt(dto.getSocialnum().substring(0, 2))+1900;
		age = age-age%10;
		String standId = map.get("id").toString();
		standTheme.add(String.valueOf(age));
		standTheme.add(dto.getGender());
		int stand = standTheme.size();
		String resultId = null;
		double result = 0;
		for(String id:dao.getAllUser()) {
			if(!(standId.equals(id))){
				map.put("id", id);
				List<String> compareTheme = dao.getUserTheme(map);
				double ins = UserUtil.intersection(standTheme, compareTheme)/standTheme.size()+compareTheme.size();
				if(result<ins) {
					result = ins;
					resultId = id;
				}
			}
		}
		
		return standTheme;
	}
	

	@Override
	public AttractionDTO selectPlace(Map map, HttpServletRequest req) {
		
		return changeOneAttr(dao.selectPlace(map), req);
	}
	@Override
	public BBSDTO getRouteBBS(int rbn,HttpServletRequest req) {
		BBSDTO dto = dao.getRouteBBS(rbn);
		dto.setRouteList(dao.selectRouteList(rbn));
		dto.setReviewList(bbsDao.reviewSelectList(rbn));
		List<String> photolist =bbsDao.bbsSelectPhotoList(rbn);
		dto.setPhoto(FileUploadUtil.requestFilePath(photolist, "/resources/bbsUpload", req));
		return dto;
	}
	@Override
	public List<BBSDTO> selectTopBBS(HttpServletRequest req) {
		List<Integer> rbnList = dao.selectTopBBS();
		List<BBSDTO> bbsList = new Vector<>();
		for(Integer rbn : rbnList) {
			BBSDTO dto = dao.getRouteBBS(rbn);
			dto.setRouteList(dao.selectRouteList(rbn));
			dto.setReviewList(bbsDao.reviewSelectList(rbn));
			List<String> photolist =bbsDao.bbsSelectPhotoList(rbn);
			dto.setPhoto(FileUploadUtil.requestFilePath(photolist, "/resources/bbsUpload", req));
			bbsList.add(dto);
		}
		return bbsList;
	}
	@Override
	public List<AttractionDTO> getTopAttr() {
		
		return dao.getTopAttr();
	}
	@Override
	public BBSDTO selectOneBBSTitle(int rbn) {
		
		return dao.selectOneBBSTitle(rbn);
	}
	@Override
	public List<BBSDTO> selectAllBbsRate() {

		return dao.selectAllBbsRate();
	}
	@Override
	public List<ReviewDTO> selectAllReview() {
		
		return dao.selectAllReview();
	}
	@Override
	public List<BBSDTO> searchBbsRate(Map map) {
	
		return dao.searchBbsRate(map);
	}
	@Override
	public List<ReviewDTO> searchBbsReview(Map map) {

		return dao.searchBbsReview(map);
	}
}
