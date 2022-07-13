package com.aamu.admin.main.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aamu.admin.main.serviceimpl.CommuServiceImpl;

@Controller
@RequestMapping("/admin/")
public class CommuController {
	
	@Autowired
	private CommuServiceImpl commuService;
	
	@RequestMapping("Commu.do")
	public String list(
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<OneMemoDTO> listPagingData= memoService.selectList(map, req, nowPage);
		
		
		
		return "commu/commu";
	}

}
