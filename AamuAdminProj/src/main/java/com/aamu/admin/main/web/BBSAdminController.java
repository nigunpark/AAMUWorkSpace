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
import com.aamu.admin.main.service.BBSAdminDTO;
import com.aamu.admin.main.service.BBSAdminService;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.ReviewAdminDTO;
import com.aamu.admin.main.serviceimpl.BBSAdminServiceImpl;
import com.aamu.admin.util.FileUploadUtil;

@Controller
public class BBSAdminController {
	
	@Autowired
	private BBSAdminServiceImpl bbsAdminService;
	
	//전체 게시글
	@RequestMapping("bbs.do")
	public String bbsAdminSelectList(
		@ModelAttribute("id") String id,
		@RequestParam Map map,
		@RequestParam(defaultValue = "1",required = false) int nowPage,
		HttpServletRequest req,
		Model model) {
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<BBSAdminDTO> listPagingData= bbsAdminService.bbsAdminSelectList(map, req, nowPage);
		
		//데이타 저장]
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",bbsAdminService.bbsGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "bbs/bbs";
	}
	
	//게시글 삭제
	@PostMapping("bbsAdminDelete.do")
	@ResponseBody
	public Map bbsAdminDelete(@RequestBody Map map){
		int affected=bbsAdminService.bbsAdminDelete(map);
		//데이터 반환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}
	
	//전체 리뷰
	@RequestMapping("review.do")
	public String reviewAdminSelectList(
		@ModelAttribute("id") String id,
		@RequestParam Map map,
		@RequestParam(defaultValue = "1",required = false) int nowPage,
		HttpServletRequest req,
		Model model) {
		//현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<ReviewAdminDTO> listPagingData= bbsAdminService.reviewAdminSelectList(map, req, nowPage);
		
		//데이타 저장]
		model.addAttribute("listPagingData",listPagingData);
		model.addAttribute("totalCount",bbsAdminService.reviewGetTotalRecordCount(map));
		
		//뷰정보 반환
		return "bbs/review";
	}
		
	
	//리뷰 삭제
	@PostMapping("reviewAdminDelete.do")
	@ResponseBody
	public Map reviewAdminDelete(@RequestBody Map map){
		int affected=bbsAdminService.reviewAdminDelete(map);
		//데이터 반환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
		}
	
	/*---------------------------------------------------------------*/
	/*
	//게시판 통계
	@RequestMapping("bbsStatistic.do")
	public String bbsStatistic(Model model, HttpServletRequest req) {
		Map map = bbsAdminService.bbsTotal();
		//월별 게시물 수
		model.addAttribute("bbsMonthTotal",map.get("bbsMonthTotal"));
		//베스트 글 
		List<BBSAdminDTO> lists = BBSAdminService.bbsBestList();
		for(BBSAdminDTO dto:lists) {
			dto.setProfile(FileUploadUtil.requestOneFile(BBSAdminService.bbsSelectProfile(dto.getId()), "/resources/bbsUpload", req));
		}
		model.addAttribute("lists",lists);
		
		return "bbs/bbsStatistic";
	}*/
		
	
	
}
