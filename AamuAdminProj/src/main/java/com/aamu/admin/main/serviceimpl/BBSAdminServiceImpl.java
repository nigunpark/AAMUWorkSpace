package com.aamu.admin.main.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class BBSAdminServiceImpl {
	
	@Autowired
	private BBSAdminDAO dao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	
	
	
	
}
