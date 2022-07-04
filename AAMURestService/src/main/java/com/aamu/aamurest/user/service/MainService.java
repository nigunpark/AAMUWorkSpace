package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import com.aamu.aamurest.user.service.PlannerDTO.Route;

public interface MainService{
	int placeInsert(AttractionDTO dto);

	int infoInsert(AttractionDTO dto);

	int hotelInsert(AttractionDTO dto);

	int dinerInsert(AttractionDTO dto);
	
	int eventInsert(AttractionDTO dto);
	
	int plannerInsert(PlannerDTO dto);
	
	int RouteInsert(Route route);
	
	List<AttractionDTO> selectPlacesList(Map map); 
}
