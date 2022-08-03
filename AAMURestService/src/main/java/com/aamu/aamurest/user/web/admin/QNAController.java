package com.aamu.aamurest.user.web.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.admin.AnswerDTO;
import com.aamu.aamurest.user.service.admin.QNADTO;
import com.aamu.aamurest.user.serviceimpl.admin.QNAServiceImpl;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin(origins = "*")
public class QNAController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private QNAServiceImpl qnaService;

	
	
	// 게시물 목록
	@GetMapping("/qna/list")
	public List<QNADTO> qnaSelectList(@RequestParam Map map, HttpServletRequest req){

		List<QNADTO> list = qnaService.qnaSelectList(map);
		for(QNADTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다

			//전체 코멘트 셋팅
			//모든 댓글 가져오기
			List<AnswerDTO> answerList=qnaService.answerList(dto.getQno());
			dto.setAnswerList(answerList);
			//써치토탈카운트
			//System.out.println("포함되어있냐"+map.keySet().contains("searchColumn"));
			if(map.keySet().contains("searchColumn")) { 
				dto.setSearchTotalCount(qnaService.qnaSearchTotalCount(map));
			}
		}/////for
		System.out.println("몇개 넘어가니:"+list.size());
		return list;
	}////////////////commuSelectList
	
	
	// 게시물 검색
	@GetMapping("/qna/search")
	public List<String> qnaSearachList(@RequestParam Map map) {
		List<String> list = qnaService.qnaSearachList(map);
		return list;
	}

	// 상세 보기
	@GetMapping("/qna/view")
	public QNADTO qnaSelectOne(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("상세 보기: " + map);
		QNADTO dto = qnaService.qnaSelectOne(map);
		List<AnswerDTO> answerList=qnaService.answerList(map.get("qno").toString());
		dto.setAnswerList(answerList);
		return dto;
	}

	// 게시물 등록
	@PostMapping(value = "/qna/write")
	public Map qnaInsert(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("등록: " + map);
		Map resultMap = new HashMap();
		int affected = qnaService.qnaInsert(map);
		if (affected == 1)
			resultMap.put("isSuccess", true);
		else
			resultMap.put("isSuccess", false);
		return resultMap;
	}

	// 게시물 수정
	@PutMapping("/qna/edit")
	public Map qnaUpdate(@RequestBody Map map) {
		System.out.println("수정: " + map);
		int qnaUpdateAffected = qnaService.qnaUpdate(map);
		Map resultMap = new HashMap<>();
		if (qnaUpdateAffected == 1)
			resultMap.put("result", "updateSuccess");
		else
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}
	
	
/*

	// 게시물 삭제
	@DeleteMapping("/qna/delete")
	public Map qnaDelete(@RequestParam Map map) {
		System.out.println("삭제: " + map);
		int qnaDeleteAffected = qnaService.qnaDelete(map);
		Map resultMap = new HashMap();
		if (qnaDeleteAffected == 1)
			resultMap.put("result", "deleteSuccess");
		else
			resultMap.put("result", "deleteNotSuccess");
		return resultMap;
	}
*/
	
	@DeleteMapping("/qna/delete")
	public Map qnaDelete(@RequestParam Map map) {
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

	/*
	// 게시물 삭제
	@DeleteMapping("/qna/edit/{qno}")
	public Map qnaDelete(@PathVariable String qno, HttpServletRequest req) {
		System.out.println("게시물 삭제 qno:"+qno);
		int affected=qnaService.qnaDelete(qno);
		Map map = new HashMap<> ();
		if(affected == 1) map.put("isSuccess", true);
		else map.put("isSuccess", false);
		return map;
	}
	*/
	
	// 댓글 등록
	@PostMapping("/answer/write")
	public Map answerInsert(@RequestBody Map map) {
		System.out.println("댓글: " + map);
		int affected = qnaService.answerInsert(map);
		Map resultMap = new HashMap<>();
		if (affected == 1) {
			resultMap.put("qno", map.get("qno"));
			resultMap.put("answer", map.get("answer"));
			resultMap.put("id", map.get("id"));
		} else
			resultMap.put("isSuccess", false);
		return resultMap;
	}

	// 댓글 수정
	@PutMapping("/answer/edit")
	public Map answerUpdate(@RequestBody Map map) {
		System.out.println("(cc)map:" + map);
		int answerUpdateAffected = qnaService.answerUpdate(map);
		Map resultMap = new HashMap();
		if (answerUpdateAffected == 1)
			resultMap.put("result", "AnswerUpdateSuccess");
		else
			resultMap.put("result", "AnswerUpdateNotSuccess");
		return resultMap;
	}

	// 댓글 삭제
	@DeleteMapping("/answer/delete")
	public Map answerDelete(@RequestParam Map map) {
		System.out.println("댓글 삭제 map:" + map);
		System.out.println("댓글 삭제 qno:" + map.get("qno"));
		System.out.println("댓글 삭제 ano:" + map.get("ano"));
		int affected = qnaService.answerDelete(map);
		// rcount -1
		// int RcMinusAffected=commuService.commuRcMinusUpdate(map);
		Map resultMap = new HashMap();
		if (affected == 1)
			resultMap.put("isSuccess", true);
		else
			resultMap.put("isSuccess", false);
		System.out.println("댓글 resultMap:" + resultMap);
		return resultMap;
	}
}