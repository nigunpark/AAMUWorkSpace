package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

public interface UsersService {
	int joinUser(Map map);

	UsersDTO selectOneUser(Map map);

	int updateUser(Map map);

	int checkId(String id);

	List<Map> selectAllTheme();

	int updateTheme(Map map);

	List selectUserTheme(Map map);


}
