package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface MainService{
	int placeInsert(AttractionDTO dto);

	int plannerInsert(PlannerDTO dto);

	PlannerDTO selectPlannerOne(int rbn,HttpServletRequest req);

	List<AttractionDTO> selectMainPlaceList(HttpServletRequest req);

	List<AttractionDTO> selectPlacesList(Map map,HttpServletRequest req);

	List<AttractionDTO> selectAttrSigungu(Map map,HttpServletRequest req);

	List<AttractionDTO> searchTwoPlace(Map map,HttpServletRequest req);

	List<AttractionDTO> searchOnePlace(Map map,HttpServletRequest req);

	int updateUrl(AttractionDTO dto);

	int updatePlaces(Map map);

	AttractionDTO selectOnePlace(int contentid,HttpServletRequest req);

	int checkPlace(Map map);

	int updateRoute(List<RouteDTO> routes);

	int updatePlanner(PlannerDTO dto);

	int deletePlanner(int rbn);


	List<AttractionDTO> getRecentPlaceAll(Map map,HttpServletRequest req);

	double getRecentPlaceOne(Map map);

	List<PlannerDTO> getPlannerList(String id);

	int updateImage(Map map);

	String searchPlanner(String message);
	
	String searchMostRoute(Map map);
	
	List<String> searchTopPlaces(Map map);

	int insertTheme(Map map);

	List<String> getUserTheme(Map map);

	AttractionDTO selectPlace(Map map, HttpServletRequest req);

	BBSDTO getRouteBBS(int rbn, HttpServletRequest req);

	List<Integer> selectTopBBS();

}
