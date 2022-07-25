package com.aamu.aamurest.aamuuser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aamu.aamurest.user.service.UsersDTO;

@Repository
public class AAMUUserDAO {

	@Autowired
	private SqlSessionTemplate template;

	public Optional<AAMUUserDTO> findByUsername(String username){
		AAMUUserDTO dto = template.selectOne("findByUsername", username);
		return Optional.ofNullable(dto);
	}

	public String getUserProfile(String id) {
		System.out.println(id);
		Map map = new HashMap<>();
		map.put("id",id);
		UsersDTO dto = template.selectOne("selectOneUser", map);
		System.out.println(dto.getId());
		return dto.getUserprofile();
	}
}
