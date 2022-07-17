package com.aamu.admin.main.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.serviceimpl.AdminCommuServiceImpl;

@Controller
public class AdminCommuController {
	
	@Autowired
	private AdminCommuServiceImpl commuService;
	
	//전체 글 뿌려주기
	@RequestMapping("Commu.do")
	public String commuSelectList(
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		
		System.out.println("search:"+map.get("searchWorld"));
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<AdminCommuDTO> listPagingData= commuService.commuSelectList(map, req, nowPage);
		
		//데이타 저장]		
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",commuService.commuGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "commu/commu";
	}
	
	//글 삭제
	@PostMapping("CommuDelete.do")
	@ResponseBody
	public Map commuDelete(@RequestBody Map map){
		int affected=commuService.commuDelete(map);
		//데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("result", "Success");
		else resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	
	//댓글 뿌려주기
	@RequestMapping("CommuComment.do")
	public String commuCommentList(
			@ModelAttribute("id") String id,
			@RequestParam Map map,
			@RequestParam(defaultValue = "1",required = false) int nowPage,
			HttpServletRequest req,
			Model model) {
		
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<AdminCommuCommentDTO> listPagingData= commuService.commuCommentList(map, req, nowPage);
		
		//데이타 저장]		
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",commuService.commuCommentGetTotalRecordCount(map));
		
		return "commu/commuComment";
	}
	
	//댓글 삭제
	@PostMapping("CommuCommentDelete.do")
	@ResponseBody
	public Map commuCommentDelete(@RequestBody Map map){
		int affected=commuService.commuCommentDelete(map);
		//데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("result", "Success");
		else resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	

}
