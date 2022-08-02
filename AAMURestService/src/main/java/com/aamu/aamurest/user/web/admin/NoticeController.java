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
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin(origins = "*")
public class NoticeController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private NoticeService noticeService;

	// 게시물 목록
	@GetMapping("/notice/selectList")
	public List<NoticeDTO> noticeSelectList(@RequestParam Map map, HttpServletRequest req) {
		// 검색할 때는 맵으로 써치워드 써치컬럼을 받고, id는 isLike때문에 받는거다. lno는 dto에서 뽑아온다
		// cid가 넘어오면 마이페이지 id에 따른 글 뿌려주기
		System.out.println("셀렉트 리스트 id:" + map.get("id"));
		System.out.println("셀렉트 리스트 searchColumn:" + map.get("searchColumn"));
		System.out.println("셀렉트 리스트 searchWord:" + map.get("searchWord"));
		// List<CommuDTO> list();
		// list=글 목록들
		List<NoticeDTO> list = noticeService.noticeSelectList(map);
		return list;
	}//////////////// commuSelectList

	// 게시물 검색
	@GetMapping("/notice/search/selectList")
	public List<String> noticeSearachList(@RequestParam Map map) {
		System.out.println("검색 searchColumn:" + map.get("searchColumn"));
		System.out.println("검색 searchWord:" + map.get("searchWord"));
		List<String> list = noticeService.noticeSearachList(map);
		return list;
	}

	// 게시물 등록
	@PostMapping(value = "/notice/write")
	public Map noticeInsert(@RequestParam Map map, HttpServletRequest req) {
		Map resultMap = new HashMap();
		int affected = noticeService.noticeInsert(map);
		if (affected == 1)
			resultMap.put("isSuccess", true);
		else
			resultMap.put("isSuccess", false);
		return resultMap;
	}

	// 게시물 상세 보기
	@GetMapping("/notice/SelectOne")
	public NoticeDTO noticeSelectOne(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("셀렉트원 map:" + map);
		NoticeDTO dto = noticeService.noticeSelectOne(map);
		return dto;
	}

	// 게시물 수정
	@PutMapping("/notice/edit")
	public Map noticeUpdate(@RequestBody Map map) {
		System.out.println("글 수정:" + map);
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
		int noticeDeleteAffected = noticeService.noticeDelete(map);
		Map resultMap = new HashMap();
		if (noticeDeleteAffected == 1)
			resultMap.put("result", "deleteSuccess");
		else
			resultMap.put("result", "deleteNotSuccess");
		return resultMap;

	}

}