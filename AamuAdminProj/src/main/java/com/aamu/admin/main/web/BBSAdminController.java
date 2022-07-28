package com.aamu.admin.main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aamu.admin.main.serviceimpl.BBSAdminServiceImpl;

@Controller
public class BBSAdminController {
	@Autowired
	private BBSAdminServiceImpl bbsAdminService;
}
