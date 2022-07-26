package com.aamu.admin.main.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.UsersDTO;
import com.aamu.admin.main.serviceimpl.AdminCommuServiceImpl;
import com.aamu.admin.main.serviceimpl.UsersServicelmpl;

@Controller
public class UsersController {
	@Autowired
	private UsersServicelmpl usersService;
	
	//전체 회원 뿌려주기
	@RequestMapping("Users.do")
	public String usersSelectList(
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<UsersDTO> listPagingData= usersService.usersSelectList(map, req, nowPage);
		
		//데이타 저장]		
		model.addAttribute("listPagingData",listPagingData);
		
		//뷰정보 반환
		return "users/users";
	}
}
