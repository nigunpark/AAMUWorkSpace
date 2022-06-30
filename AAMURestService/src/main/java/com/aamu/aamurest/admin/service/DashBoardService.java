package com.aamu.aamurest.admin.service;

import java.util.Map;

public interface DashBoardService {
	int userTotalCount();
	int userJoinCount(Map map);
	int commuCount();
	int bbsCount();
	
	int userRating();
	Map categoryCount();
}
