package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface MainService{
	int placeInsert(AttractionDTO dto);

	int plannerInsert(PlannerDTO dto);

	PlannerDTO selectPlannerOne(int rbn);

	List<AttractionDTO> selectMainPlaceList();

	List<AttractionDTO> selectPlacesList(Map map,HttpServletRequest req);

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


	List<AttractionDTO> getRecentPlaceAll(Map map);

	double getRecentPlaceOne(Map map);

	List<PlannerDTO> getPlannerList(String id);

	int updateImage(Map map);

	String searchPlanner(String message);
	
	String searchMostRoute(Map map);
	
	List<String> searchTopPlaces(Map map);

	int insertTheme(Map map);

	List<String> getUserTheme(Map map);

}
