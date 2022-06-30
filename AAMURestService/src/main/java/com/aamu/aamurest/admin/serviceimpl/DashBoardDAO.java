package com.aamu.aamurest.admin.serviceimpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashBoardDAO {
	private SqlSessionTemplate template;

	public int userTotalCount() {
		
		return template.selectOne("userTotalCount");
	}
	
}
