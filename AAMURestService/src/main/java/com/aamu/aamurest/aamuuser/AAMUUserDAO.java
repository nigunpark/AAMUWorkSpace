package com.aamu.aamurest.aamuuser;

import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AAMUUserDAO {

	@Autowired
	private SqlSessionTemplate template;

	public Optional<AAMUUserDTO> findByUsername(String username){
		AAMUUserDTO dto = template.selectOne("findByUsername", username);
		return Optional.ofNullable(dto);
	}
}
