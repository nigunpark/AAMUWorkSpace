package com.aamu.aamurest.user.serviceimpl;

import java.util.List;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@Repository
public class MainDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//////////////////////////api insert 용 dao
	public int placeInsert(AttractionDTO dto) {
		
		
		return template.insert("placeInsert",dto);
	}

	public int infoInsert(AttractionDTO dto) {
		
		return template.insert("infoInsert",dto);
	}

	public int hotelInsert(AttractionDTO dto) {
		
		return template.insert("hotelInsert",dto);
	}

	public int dinerInsert(AttractionDTO dto) {
		return template.insert("dinerInsert",dto);
	}

	public int eventInsert(AttractionDTO dto) {
		
		return template.insert("eventInsert",dto);
	}
	
	
	//////////////////////////데이터 전송용 dao
	public List<AttractionDTO> selectPlacesList(Map map) {
		
		return template.selectList("selectPlacesList", map);
	}
	////////////////////////////플래너용 dao
	public int plannerInsert(PlannerDTO dto) {

		return template.insert("plannerInsert",dto);
	}

	public int routeInsert(RouteDTO route) {
		
		return template.insert("routeInsert",route);
	}

	public List<RouteDTO> selectRouteList(int rbn) {
		
		return template.selectList("selectRouteList",rbn);
	}

	public List<AttractionDTO> selectAttrSigungu(Map map) {
		
		return template.selectList("selectAttrSigungu",map);
	}

	public List<AttractionDTO> selectEventSigungu(Map map) {
		
		return template.selectList("selectEventSigungu",map);
	}

	public List<AttractionDTO> selectHotelSigungu(Map map) {
		
		return template.selectList("selectHotelSigungu", map);
	}

	public List<AttractionDTO> selectDinerSigungu(Map map) {

		return template.selectList("selectDinerSigungu", map);
	}

	public List<AttractionDTO> selectEventList(Map map) {
		// TODO Auto-generated method stub
		return null;
	}


}
