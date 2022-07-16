package com.aamu.aamurest.user.service;

import java.util.List;

import java.util.Map;


public interface MainService{
	int placeInsert(AttractionDTO dto);

	int plannerInsert(PlannerDTO dto);
	
	List<RouteDTO> selectRouteList(int rbn); 
	
	List<AttractionDTO> selectPlacesList(Map map);

	List<AttractionDTO> selectAttrSigungu(Map map);

	List<AttractionDTO> searchTwoPlace(Map map);

	List<AttractionDTO> searchOnePlace(Map map);
	
	int updateUrl(AttractionDTO dto);
	
	int updatePlaces(Map map);

	AttractionDTO selectOnePlace(int contentid);
	
	int checkPlace(Map map);

	int updateRoute(List<RouteDTO> routes);

	int updatePlanner(PlannerDTO dto);

	int deletePlanner(int rbn);

	int deleteRoute(int rbn);

	List<AttractionDTO> getRecentPlaceAll(Map map);
	
	double getRecentPlaceOne(Map map);
	
}
