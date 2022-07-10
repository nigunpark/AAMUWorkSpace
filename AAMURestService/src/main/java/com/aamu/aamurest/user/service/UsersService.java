package com.aamu.aamurest.user.service;

import java.util.Map;

public interface UsersService {
	int joinUser(UsersDTO dto);
	
	UsersDTO selectOneUser(Map map);
	
	int updateUser(UsersDTO dto);
	
}
