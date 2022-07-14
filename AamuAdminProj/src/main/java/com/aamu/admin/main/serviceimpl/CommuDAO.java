package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.admin.main.service.CommuDTO;

@Repository
public class CommuDAO {
	@Autowired
	private SqlSessionTemplate template;
	
	//전체 레코드수
		public int commuGetTotalRecordCount(Map map) {
			return template.selectOne("commuGetTotalRecordCount", map);
		}
	
	//글 리스트 뿌려주기
	public List<CommuDTO> commuSelectList(Map map){
		List<CommuDTO> records = template.selectList("commuSelectList",map);
		return records;
	}
	
	//글 삭제
	public int commuDelete(Map map) {
		return template.delete("commuDelete",map);
	}
	
	

}
