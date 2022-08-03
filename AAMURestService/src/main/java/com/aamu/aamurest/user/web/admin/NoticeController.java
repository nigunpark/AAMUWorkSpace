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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.admin.NoticeDTO;
import com.aamu.aamurest.user.service.admin.NoticeService;
import com.aamu.aamurest.user.serviceimpl.admin.NoticeServiceImpl;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin(origins = "*")
public class NoticeController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private NoticeServiceImpl noticeService;

	// 게시물 목록
	@GetMapping("/notice/list")
	public List<NoticeDTO> noticeSelectList(@RequestParam Map map, HttpServletRequest req) {
		List<NoticeDTO> list = noticeService.noticeSelectList(map);
		return list;
	}

	// 게시물 검색
	@GetMapping("/notice/search")
	public List<String> noticeSearachList(@RequestParam Map map) {
		List<String> list = noticeService.noticeSearachList(map);
		return list;
	}

	// 상세 보기
	@GetMapping("/notice/view")
	public NoticeDTO noticeSelectOne(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("상세 보기: " + map);
		NoticeDTO dto = noticeService.noticeSelectOne(map);
		return dto;
	}

	// 게시물 등록
	@PostMapping(value = "/notice/write")
	public Map noticeInsert(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("등록: " + map);
		Map resultMap = new HashMap();
		int affected = noticeService.noticeInsert(map);
		if (affected == 1)
			resultMap.put("isSuccess", true);
		else
			resultMap.put("isSuccess", false);
		return resultMap;
	}

	// 게시물 수정
	@PutMapping("/notice/edit")
	public Map noticeUpdate(@RequestBody Map map) {
		System.out.println("수정: " + map);
		int noticeUpdateAffected = noticeService.noticeUpdate(map);
		Map resultMap = new HashMap<>();
		if (noticeUpdateAffected == 1)
			resultMap.put("result", "updateSuccess");
		else
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}

	// 게시물 삭제
	@DeleteMapping("/notice/delete")
	public Map noticeDelete(@RequestParam Map map) {
		System.out.println("삭제: " + map);
		int noticeDeleteAffected = noticeService.noticeDelete(map);
		Map resultMap = new HashMap();
		if (noticeDeleteAffected == 1)
			resultMap.put("result", "deleteSuccess");
		else
			resultMap.put("result", "deleteNotSuccess");
		return resultMap;
	}

}