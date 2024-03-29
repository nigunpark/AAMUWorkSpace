package com.aamu.admin.main.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.serviceimpl.AdminCommuServiceImpl;
import com.aamu.admin.util.FileUploadUtil;

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
	public Map commuDelete(@RequestBody Map map/*, HttpServletRequest req*/){
		System.out.println("삭제 map:"+map);
		//사진삭제
		/*
		List<String> photoLists=commuService.commuSelectPhotoList(map);
		String path=req.getSession().getServletContext().getRealPath("/resources/commuUpload");
		
		try {
			FileUploadUtil.fileDeletes(photoLists, path);
		} catch (IllegalStateException | IOException e) {}
		*/
		
		
		int affected=commuService.commuDelete(map);
		//데이타 반환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
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
		if(affected==1) resultMap.put("result", "Success");
		else resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	
	////////////////////////////////////////////////////////////////////
	
	//커뮤니티 통계
	@RequestMapping("CommuStatistics.do")
	public String commuStatistics(Model model, HttpServletRequest req) {
		Map map = commuService.commuTotal();
		//월별 게시물 수
		model.addAttribute("commuMonthTotal",map.get("commuMonthTotal"));
		//성별 게시물 수 
		model.addAttribute("femaleRecordCount",map.get("femaleRecordCount")); 
		model.addAttribute("maleRecordCount",map.get("maleRecordCount"));
		
		//베스트 글쓴이 
		List<AdminCommuDTO> lists = commuService.bestUsersList();
		for(AdminCommuDTO dto:lists) {
			dto.setUserprofile(FileUploadUtil.requestOneFile(commuService.commuSelectUserProf(dto.getId()), "/resources/userUpload", req));
		}
		model.addAttribute("lists",lists);
		
		return "commu/commuStatistics";
	}
	

	
	

}
