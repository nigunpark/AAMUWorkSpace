package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainDAO dao;
	
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
		String tel = dto.getTel();
		if(tel!=null) {
			if(tel.contains("<br />")) {
				tel.replace("<br />", "\r\n");
			}
			else if(tel.contains("<br/>")){
				tel.replace("<br/>", "\r\n");
			}
			else if(tel.contains("<br>")){
				tel.replace("<br>", "\r\n");
			}
			dto.setTel(tel);
			if(tel.length()>100) {
				dto.setTel("");
			}
		}
		return dao.placeInsert(dto);
	}
	@Override
	public int infoInsert(AttractionDTO dto) {
		String playtime = dto.getPlaytime();
		String resttime = dto.getResttime();
		if(playtime!=null) {
			if(playtime.contains("<br />")) {
				playtime.replace("<br />", "\r\n");
			}
			else if(playtime.contains("<br/>")){
				playtime.replace("<br/>", "\r\n");
			}
			else if(playtime.contains("<br>")){
				playtime.replace("<br>", "\r\n");
			}
			dto.setPlaytime(playtime);
		}
		if(resttime!=null) {
			if(resttime.contains("<br />")) {
				resttime.replace("<br />", "\r\n");
			}
			else if(resttime.contains("<br/>")){
				resttime.replace("<br/>", "\r\n");
			}
			else if(resttime.contains("<br>")){
				resttime.replace("<br>", "\r\n");
			}
			dto.setResttime(resttime);
		}
		return dao.infoInsert(dto);
	}
	@Override
	public int hotelInsert(AttractionDTO dto) {
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
		return dao.hotelInsert(dto);
	}
	@Override
	public int dinerInsert(AttractionDTO dto) {
		
		return dao.dinerInsert(dto);
	}
	@Override
	public int eventInsert(AttractionDTO dto) {
		String start = dto.getEventstart();
		String end = dto.getEventend();
		start = start.substring(0, 4) +"-"+start.substring(4, 6)+"-"+start.substring(6,8);
		end = end.substring(0, 4) +"-"+end.substring(4, 6)+"-"+end.substring(6,8);
		dto.setEventstart(start);
		dto.setEventend(end);
		String eventTime = dto.getEventtime();
		if(eventTime!=null) {
			if(eventTime.contains("<br>")) {
				eventTime.replace("<br>", "\r\n");
			}
			else if(eventTime.contains("<br />")) {
				eventTime.replace("<br />", "\r\n");
			}
			else if(eventTime.contains("<br/>")){
				eventTime.replace("<br/>", "\r\n");
			}
			dto.setEventtime(eventTime);
		}
		String charge = dto.getCharge();
		if(charge!=null) {
			if(charge.contains("<br>")) {
				charge.replace("<br>", "\r\n");
				
			}
			else if(charge.contains("<br />")) {
				charge.replace("<br />", "\r\n");
			}
			else if(charge.contains("<br/>")){
				charge.replace("<br/>", "\r\n");
			}
			if(charge.length()>100) {
				charge = dto.getUrl();
			}
			dto.setCharge(charge);
		}
		return dao.eventInsert(dto);
	}
	@Override
	public int plannerInsert(PlannerDTO dto) {
		
		return dao.plannerInsert(dto);
	}
	@Override
	public int RouteInsert(RouteDTO route) {
		// TODO Auto-generated method stub
		return dao.routeInsert(route);
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
	@Override
	public List<AttractionDTO> selectEventSigungu(Map map) {
		
		return dao.selectEventSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectHotelSigungu(Map map) {
		
		return dao.selectHotelSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectDinerSigungu(Map map) {
		
		return dao.selectDinerSigungu(map);
	}
	@Override
	public List<AttractionDTO> selectEventList(Map map) {
		return dao.selectEventList(map);
	}
	@Override
	public List<AttractionDTO> selectHotelList(Map map) {
		return dao.selectHotelList(map);
	}
	@Override
	public List<AttractionDTO> selectDinerList(Map map) {
		return dao.selectDinerList(map);
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
	public int updateUrl(AttractionDTO dto) {
		// TODO Auto-generated method stub
		return dao.updateUrl(dto);
	}
	////////////////////////////////////selectone place
	@Override
	public AttractionDTO selectOnePlace(int contentid) {
		
		return dao.selectOnePlace(contentid);
	}
	@Override
	public int checkPlace(int contentid) {
		
		return dao.checkPlace(contentid);
	}


}
