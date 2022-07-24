package com.aamu.admin.main.service;

import java.util.Date;

import java.util.List;
import java.util.Map;

import com.aamu.admin.main.service.api.AttractionDTO;


public interface MainService {
	
	
	int usersTotalCount();
	int usersTodayCount();
	Map placesTotalCount();
	int selectEvent();
	Map selectWeek();
	Map<String,List> selectStartEnd(Map map);
	int placeInsert(AttractionDTO dto);
	int checkPlace(Map map);
	Map countAllPlaces();

}
