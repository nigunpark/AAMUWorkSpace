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

	//////////////////////////api insert dao
	public int placeInsert(AttractionDTO dto) {


		return template.insert("placeInsert",dto);
	}

	//////////////////////////get place dao
	public List<AttractionDTO> selectPlacesList(Map map) {

		return template.selectList("selectPlacesList", map);
	}
	public List<AttractionDTO> selectAttrSigungu(Map map) {

		return template.selectList("selectAttrSigungu",map);
	}
	////////////////////////////planner dao
	public int plannerInsert(PlannerDTO dto) {

		return template.insert("plannerInsert",dto);
	}

	public int routeInsert(RouteDTO route) {

		return template.insert("routeInsert",route);
	}

	public List<AttractionDTO> searchTwoPlace(Map map) {

		return template.selectList("searchTwoPlace", map);
	}
	///////////////////////////////////////////////////serchdao
	public List<AttractionDTO> searchOnePlace(Map map) {

		return template.selectList("searchOnePlace", map);
	}

	public int updatePlaces(Map map) {
		// TODO Auto-generated method stub
		return template.update("updatePlaces",map);
	}
	public int updateUrl(AttractionDTO dto) {

			return template.update("updateUrl",dto);
		}

	public AttractionDTO selectOnePlace(int contentid) {

		return template.selectOne("selectOnePlace", contentid);
	}

	public int checkPlace(Map map) {

		return template.selectOne("checkPlace", map);
	}

	public int updateRoute(List<RouteDTO> routes) {

		return template.update("updateRoute", routes);
	}

	public int updatePlanner(PlannerDTO dto) {

		return template.update("updatePlanner", dto);
	}

	public int deletePlanner(Map map) {

		return template.delete("deletePlanner",map);
	}

	public int deleteRoute(int rbn) {

		return template.delete("deleteRoute",rbn);
	}

	public List<AttractionDTO> getRecentPlaceAll(Map map) {

		return template.selectList("getRecentPlaceAll", map);
	}

	public AttractionDTO selectPlace(Map map) {

		return template.selectOne("selectPlace",map);
	}

	public double getRecentPlaceOne(Map map) {

		return template.selectOne("getRecentPlaceOne", map);
	}

	public List<PlannerDTO> getPlannerList(String id) {

		return template.selectList("getPlannerList", id);
	}

	public PlannerDTO selectPlannerOne(int rbn) {

		return template.selectOne("selectPlannerOne", rbn);
	}

	public List<RouteDTO> selectRouteList(int rbn) {

		return template.selectList("selectRouteList",rbn);
	}

	public List<AttractionDTO> selectMainPlaceList() {
		return template.selectList("selectMainPlaceList");
	}

	public int updateImage(Map map) {
		
		return template.update("updateImage",map);
	}

	public String searchPlanner(String message) {
		//String rbn = String.valueOf(template.selectOne("searchPlanner",message));
		//System.out.println(rbn);
		return template.selectOne("searchPlanner",message);
	}

	public int countRouteBBS(int rbn) {
		
		return template.selectOne("countRouteBBS",rbn);
	}
	public String searchMostRoute(Map map) {
		return template.selectOne("searchMostRoute", map);
	}

}
