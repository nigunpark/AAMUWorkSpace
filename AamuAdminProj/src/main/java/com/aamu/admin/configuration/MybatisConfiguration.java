package com.aamu.admin.configuration;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class MybatisConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SqlSessionTemplate template() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(new ClassPathResource("admin/mybatis/configuration.xml"));
		return new SqlSessionTemplate(sqlSessionFactory.getObject());
	}
}
