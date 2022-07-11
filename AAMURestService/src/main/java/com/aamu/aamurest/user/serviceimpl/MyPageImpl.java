package com.aamu.aamurest.user.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myPageService")
public class MyPageImpl {
	@Autowired
	private MyPageDAO dao;

}
