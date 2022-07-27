package com.aamu.admin.main.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.UsersDTO;
import com.aamu.admin.main.serviceimpl.AdminCommuServiceImpl;
import com.aamu.admin.main.serviceimpl.UsersServicelmpl;
import com.aamu.admin.util.FileUploadUtil;

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
		//UsersDTO dto;
		//dto.setUserprofile(FileUploadUtil.requestOneFile(usersService.usersSelectUserProf(dto.getId()), "/resources/commuUpload", req));
		ListPagingData<UsersDTO> listPagingData= usersService.usersSelectList(map, req, nowPage);
		List<UsersDTO> list=listPagingData.getLists();
		
		//유저 프로필
		for(UsersDTO dto:list) {
			dto.setUserprofile(FileUploadUtil.requestOneFile(usersService.usersSelectUserProf(dto.getId()), "/resources/userUpload", req));
		}
		//listPagingData.setLists(list);
		
		
		//데이타 저장]		
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",usersService.usersGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "users/users";
	}
	
	//회원 정지
	@PostMapping("UsersStop.do")
	@ResponseBody
	public Map usersDelete(@RequestBody Map map) {
		System.out.println("컨트롤러 id인가:"+map.get("stopId"));
		System.out.println("컨트롤러 id인가:"+map.get("addId"));
		int affected=usersService.usersStop(map);
		//데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}
	
}
