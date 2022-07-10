package com.aamu.admin.main.serviceimpl;

import java.util.Date;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAO {
	@Autowired
	private SqlSessionTemplate template;
	
	public int usersTotalCount() {
		
		return template.selectOne("usersTotalCount");
	}

	public int usersTodayCount(String today) {
		
		return template.selectOne("usersTodayCount",today);
	}

	public int placesTotalCount(Map map) {
		
		return template.selectOne("placesTotalCount",map);
	}

	public int selectPeriod(Map map) {
		
		return template.selectOne("selectPeriod",map);
	}

}
