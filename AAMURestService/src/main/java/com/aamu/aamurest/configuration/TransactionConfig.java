package com.aamu.aamurest.configuration;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TransactionConfig {
	
	@Autowired
	private DataSource datasource;
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		
		
		return new DataSourceTransactionManager(datasource);
	}
	@Bean
	public TransactionTemplate transactionTemplate() {
		
		return new TransactionTemplate(transactionManager());
	}
}
