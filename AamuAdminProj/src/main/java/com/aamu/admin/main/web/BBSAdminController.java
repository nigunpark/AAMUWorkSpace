package com.aamu.admin.main.web;

import java.util.HashMap;
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
import com.aamu.admin.main.service.BBSAdminDTO;
import com.aamu.admin.main.service.BBSAdminService;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.ReviewAdminDTO;
import com.aamu.admin.main.serviceimpl.BBSAdminServiceImpl;

@Controller
public class BBSAdminController {
	
	@Autowired
	private BBSAdminServiceImpl bbsAdminService;
	
	//전체 게시글
	@RequestMapping("bbs.do")
	public String bbsSelectList(
		@ModelAttribute("id") String id,
		@RequestParam Map map,
		@RequestParam(defaultValue = "1",required = false) int nowPage,
		HttpServletRequest req,
		Model model) {
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<BBSAdminDTO> listPagingData= bbsAdminService.bbsSelectList(map, req, nowPage);
		
		//데이타 저장]
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",bbsAdminService.bbsGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "bbs/bbs";
	}
	
	//게시글 삭제
	@PostMapping("bbsDelete.do")
	@ResponseBody
	public Map bbsDelete(@RequestBody Map map){
		int affected=bbsAdminService.bbsDelete(map);
		//데이터 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}
	
	//전체 리뷰
	@RequestMapping("review.do")
	public String reviewSelectList(
		@ModelAttribute("id") String id,
		@RequestParam Map map,
		@RequestParam(defaultValue = "1",required = false) int nowPage,
		HttpServletRequest req,
		Model model) {
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<ReviewAdminDTO> listPagingData= bbsAdminService.reviewSelectList(map, req, nowPage);
		
		//데이타 저장]
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",bbsAdminService.reviewGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "review/review";
	}
		
	
	//리뷰 삭제
	@PostMapping("reviewDelete.do")
	@ResponseBody
	public Map reviewDelete(@RequestBody Map map){
		int affected=bbsAdminService.reviewDelete(map);
		//데이터 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
		}
	
	
	
}
