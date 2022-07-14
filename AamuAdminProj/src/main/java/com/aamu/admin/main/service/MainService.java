package com.aamu.admin.main.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MainService {
	
	
	int usersTotalCount();
	int usersTodayCount(String today);
	int placesTotalCount(Map map);
	int selectPeriod(Map map);
	List selectJoin(List day);
	int selectUsers(Map map);
	
}
