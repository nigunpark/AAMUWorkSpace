package com.aamu.admin.main.serviceimpl;

import java.util.Date;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAO {
	
	private SqlSessionTemplate template;
	
	public int usersTotalCount() {
		
		return template.selectOne("usersTotalCount");
	}

	public int usersTodayCount(Date today) {
		
		return template.selectOne("usersTodayCount",today);
	}

	public int placesTotalCount(Map map) {
		
		return template.selectOne("placesTotalCount",map);
	}

}
