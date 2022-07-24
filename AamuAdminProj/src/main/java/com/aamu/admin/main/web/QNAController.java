package com.aamu.admin.main.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.QNADTO;
import com.aamu.admin.main.serviceimpl.QNAServiceImpl;

@Controller
public class QNAController {

	@Autowired
	private QNAServiceImpl qnaService;

	// 전체 글 뿌려주기
	@RequestMapping("QNA.do")
	public String qnaSelectList(@ModelAttribute("id") String id, @RequestParam Map map,
			@RequestParam(defaultValue = "1", required = false) int nowPage, HttpServletRequest req, Model model) {
		// 현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<QNADTO> listPagingData = qnaService.qnaSelectList(map, req, nowPage);

		// 데이타 저장]
		model.addAttribute("listPagingData", listPagingData);
		model.addAttribute("totalCount", qnaService.qnaGetTotalRecordCount(map));

		// 뷰정보 반환
		return "qna/qnaList";
	}
	
	
	//상세보기]
	@RequestMapping("QNAView.do")
	public String qnaView(
			//@ModelAttribute("id") String id,
			@RequestParam Map map, Model model) throws Exception {
	
		qnaService.qnaCount(map);
	
		//서비스 호출]
		QNADTO record = qnaService.selectOne(map);
		
		//데이타 저장]		
		model.addAttribute("record", record);
		//뷰정보 반환]
		return "qna/QNAView";
	}///////////////////////
	
	


	// 글 삭제
	@PostMapping("QNADelete.do")
	@ResponseBody
	public Map qnaDelete(@RequestBody Map map) {
		int affected = qnaService.qnaDelete(map);
		// 데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:" + affected);
		if (affected == 1)
			resultMap.put("result", "Success");
		else
			resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	
	
	@GetMapping("QNAViewDelete.do")
	public String delete(@ModelAttribute("qno") String nno,@RequestParam Map map) {
		
		qnaService.qnaViewDelete(map);
		
		return "redirect:/QNA.do";
	}/////////////////////////
	
	

}