package com.aamu.aamurest.user.service;

import java.util.Map;

public interface UsersService {
	int joinUser(Map map);
	
	UsersDTO selectOneUser(Map map);
	
	int updateUser(UsersDTO dto);

	int checkId(String id);
	
}
