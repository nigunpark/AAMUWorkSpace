package com.aamu.aamurest.user.service;

import java.util.List;

import java.util.Map;


public interface MainService{
	int placeInsert(AttractionDTO dto);

	int infoInsert(AttractionDTO dto);

	int hotelInsert(AttractionDTO dto);

	int dinerInsert(AttractionDTO dto);
	
	int eventInsert(AttractionDTO dto);
	
	int plannerInsert(PlannerDTO dto);
	
	int RouteInsert(RouteDTO route);
	
	List<RouteDTO> selectRouteList(int rbn); 
	
	List<AttractionDTO> selectPlacesList(Map map);

	List<AttractionDTO> selectAttrSigungu(Map map);

	List<AttractionDTO> selectEventSigungu(Map map);

	List<AttractionDTO> selectHotelSigungu(Map map);

	List<AttractionDTO> selectDinerSigungu(Map map);

	List<AttractionDTO> selectEventList(Map map);

	List<AttractionDTO> selectHotelList(Map map);

	List<AttractionDTO> selectDinerList(Map map);

	
}
