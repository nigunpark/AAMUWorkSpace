package com.aamu.admin.main.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.api.AttractionDTO;

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

	public int selectEvent() {
		
		return template.selectOne("selectEvent");
	}

	public int selectWeek(Map map) {
		
		return template.selectOne("selectWeek",map);
	}
	public int selectWeekToString(Map map) {
		return template.selectOne("selectWeekToString", map);
	}

	public int selectUsers(String day) {
		
		return template.selectOne("selectUsers",day);
	}

	public String selectDate(String day) {
	
		return template.selectOne("selectDate", day);
	}
	public int selectUsersToString(String day) {
		return template.selectOne("selectUsersToString",day);
	}

	public int countAllPlaces(Map map) {
		
		return template.selectOne("countAllPlaces",map);
	}

	public int placeInsert(AttractionDTO dto) {
		
		return template.insert("placeInsert",dto);
	}

}
