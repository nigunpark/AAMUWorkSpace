package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.ThemeDTO;

@Repository
public class ThemeDAO {
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 테마 뿌려주기
	public List<ThemeDTO> themeSelectList(Map map){
		List<ThemeDTO> records = template.selectList("themeSelectList",map);
		return records;
	}
	
	//글 전체 레코드수
	public int themeGetTotalRecordCount(Map map) {
		return template.selectOne("themeGetTotalRecordCount", map);
	}
	
	//테마이미지
	public String getThemeImage(String themeid) {
		return template.selectOne("getThemeImage",themeid);
	}

}
