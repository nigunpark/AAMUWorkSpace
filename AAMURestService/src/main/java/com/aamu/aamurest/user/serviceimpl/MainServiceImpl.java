package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainDAO dao;

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
	public List<RouteDTO> selectRouteList(int rbn) {
		
		return dao.selectRouteList(rbn);
	}
	@Override
	public List<AttractionDTO> selectPlacesList(Map map) {
		
		return dao.selectPlacesList(map);
	}
	@Override
	public List<AttractionDTO> selectAttrSigungu(Map map) {
		
		return dao.selectAttrSigungu(map);
	}
///////////////////////////////////////////////////search place impl
	@Override
	public List<AttractionDTO> searchTwoPlace(Map map) {
		
		return dao.searchTwoPlace(map);
	}

	@Override
	public List<AttractionDTO> searchOnePlace(Map map) {
		
		return dao.searchOnePlace(map);
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
	public AttractionDTO selectOnePlace(int contentid) {
		
		return dao.selectOnePlace(contentid);
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
		
		return dao.deletePlanner(rbn);
	}
	@Override
	public int deleteRoute(int rbn) {
		
		return dao.deleteRoute(rbn);
	}
	@Override
	public List<AttractionDTO> getRecentPlaceAll(Map map) {
		List<AttractionDTO> lists = dao.getRecentPlaceAll(map);
		List<AttractionDTO> returnList = new Vector<>();
		for(int i=0;i<lists.size();i++) {
			AttractionDTO dto =lists.get(i);
			switch(dto.getContenttypeid()) {
			case 12:
			case 28:
				map.put("selecttable", "placesinfo");
				map.put("contentid", dto.getContentid());
				dto = dao.selectPlace(map);
				break;
			case 15:
				map.put("selecttable", "eventinfo");
				map.put("contentid", dto.getContentid());
				dto = dao.selectPlace(map);
				break;
			case 32:
				map.put("selecttable", "hotelinfo");
				map.put("contentid", dto.getContentid());
				dto = dao.selectPlace(map);
				break;
			case 39:
				map.put("selecttable", "dinerinfo");
				map.put("contentid", dto.getContentid());
				dto = dao.selectPlace(map);
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



}
