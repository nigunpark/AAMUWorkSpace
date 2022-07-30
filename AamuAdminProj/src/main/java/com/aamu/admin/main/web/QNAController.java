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
import com.aamu.admin.main.service.QNADTO;
import com.aamu.admin.main.serviceimpl.QNAServiceImpl;

@Controller
public class QNAController {

	@Autowired
	private QNAServiceImpl qnaService;

	// 게시물 목록
	@RequestMapping("QNA.do")
	public String selectList(@ModelAttribute("id") String id, @RequestParam Map map,
			@RequestParam(defaultValue = "1", required = false) int nowPage, HttpServletRequest req, Model model) {
		// 현재 페이지를 맵에 저장
		map.put(PagingUtil.NOW_PAGE, nowPage);
		ListPagingData<QNADTO> listPagingData = qnaService.selectList(map, req, nowPage);
		// 데이타 저장
		model.addAttribute("listPagingData", listPagingData);
		model.addAttribute("totalCount", qnaService.qnaGetTotalRecordCount(map));
		// 뷰정보 반환
		return "qna/qnaList";
	}

	// 상세 보기
	@RequestMapping("QNAView.do")
	public String qnaView(
			// @ModelAttribute("id") String id,
			@RequestParam Map map, Model model) throws Exception {
		qnaService.qnaCount(map);
		// 서비스 호출]
		QNADTO record = qnaService.selectOne(map);
		// 데이타 저장]
		model.addAttribute("record", record);
		// 뷰정보 반환]
		return "qna/qnaView";
	}///////////////////////

	// 게시물 등록
	@GetMapping("QNAWrite.do")
	public String qnaWrite() throws Exception {
		return "qna/qnaWrite";
	}

	// 게시물 등록 완료
	@PostMapping("QNAWrite.do")
	public String qnaWriteOk(@RequestParam Map map) {
		map.put("id", "ADMIN2");
		qnaService.qnaWrite(map);
		return "redirect:/QNA.do";
	}

	// 게시물 수정
	@GetMapping("QNAEdit.do")
	public String qnaEdit(@ModelAttribute("qno") String qno, @RequestParam Map map, Model model) throws Exception {
		// 서비스 호출]
		QNADTO record = qnaService.selectOne(map);
		record.setContent(record.getContent().replace("<br/>", "\r\n"));
		// 데이타 저장]
		model.addAttribute("record", record);
		// 뷰정보 반환]
		return "qna/qnaEdit";
	}

	// 게시물 수정 완료
	@PostMapping("QNAEdit.do")
	public String qnaEditOk(@ModelAttribute("qno") String qno, @RequestParam Map map) throws Exception {
		// 서비스 호출]
		qnaService.qnaEdit(map);
		// 뷰정보 반환]-목록을 처리하는 컨트롤러로 이동
		return "redirect:/QNAView.do";
	}/////////////////////////

	// 게시물 목록에서 삭제
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

	// 상세 보기에서 삭제
	@GetMapping("QNAViewDelete.do")
	public String delete(@ModelAttribute("qno") String qno, @RequestParam Map map) {
		qnaService.qnaViewDelete(map);
		return "redirect:/QNA.do";
	}

}