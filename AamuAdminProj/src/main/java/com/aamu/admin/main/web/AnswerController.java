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
import com.aamu.admin.main.service.NoticeDTO;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.serviceimpl.NoticeServiceImpl;

@Controller
public class AnswerController {

	@Autowired
	private NoticeServiceImpl noticeService;

	// 전체 글 뿌려주기
	@RequestMapping("Notice.do")
	public String noticeSelectList(@ModelAttribute("id") String id, @RequestParam Map map,
			@RequestParam(defaultValue = "1", required = false) int nowPage, HttpServletRequest req, Model model) {
		// 현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<NoticeDTO> listPagingData = noticeService.noticeSelectList(map, req, nowPage);

		// 데이타 저장]
		model.addAttribute("listPagingData", listPagingData);
		model.addAttribute("totalCount", noticeService.noticeGetTotalRecordCount(map));

		// 뷰정보 반환
		return "notice/noticeList";
	}
	
	
	//상세보기]
	@RequestMapping("NoticeView.do")
	public String noticeView(
			//@ModelAttribute("id") String id,
			@RequestParam Map map, Model model) throws Exception {
	
		noticeService.noticeCount(map);
	
		//서비스 호출]
		NoticeDTO record = noticeService.selectOne(map);
		
		//데이타 저장]		
		model.addAttribute("record", record);
		//뷰정보 반환]
		return "notice/noticeView";
	}///////////////////////
	
	
	
	
	// 글 등록
	@GetMapping("NoticeWrite.do")
	public String noticeWrite() throws Exception {
		return "notice/noticeWrite";
	}

	@PostMapping("NoticeWrite.do")
	public String noticeWriteOk(@RequestParam Map map) {
		map.put("id", "ADMIN2");
		noticeService.noticeWrite(map);
		return "redirect:/Notice.do";
	}
	
	
	@GetMapping("NoticeEdit.do")
	public String noticeEdit(@ModelAttribute("nno") String nno,@RequestParam Map map,Model model) throws Exception {
		
		//서비스 호출]
		NoticeDTO record= noticeService.selectOne(map);
		
		record.setContent(record.getContent().replace("<br/>","\r\n"));
		
		//데이타 저장]		
		model.addAttribute("record", record);
		//뷰정보 반환]
		return "notice/noticeEdit";
	}///////////////////////
	
	@PostMapping("NoticeEdit.do")
	public String noticeEdit(@ModelAttribute("nno") String nno,@RequestParam Map map) throws Exception {
		//서비스 호출]		
		noticeService.noticeEdit(map);
		//뷰정보 반환]-목록을 처리하는 컨트롤러로 이동
		return "redirect:/NoticeView.do";
	}/////////////////////////
	

	// 글 삭제
	@PostMapping("NoticeDelete.do")
	@ResponseBody
	public Map noticeDelete(@RequestBody Map map) {
		int affected = noticeService.noticeDelete(map);
		// 데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:" + affected);
		if (affected == 1)
			resultMap.put("result", "Success");
		else
			resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	
	
	@GetMapping("NoticeViewDelete.do")
	public String delete(@ModelAttribute("nno") String nno,@RequestParam Map map) {
		
		noticeService.noticeViewDelete(map);
		
		return "redirect:/Notice.do";
	}/////////////////////////
	
	

}