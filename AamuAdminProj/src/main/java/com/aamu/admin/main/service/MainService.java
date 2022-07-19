package com.aamu.admin.main.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MainService {
	
	
	int usersTotalCount();
	int usersTodayCount();
	Map placesTotalCount();
	int selectEvent();
	Map selectWeek();
	Map<String,List> selectStartEnd(Map map);

}
