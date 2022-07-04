package com.aamu.aamurest.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.serviceimpl.CommuServiceImpl;

@RestController
@CrossOrigin(origins="*")
public class CommuController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommuServiceImpl commuService;
	

	//목록용
	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(){
		//list=작성한 글들
		List<CommuDTO> list = commuService.commuSelectList();
		//코맨트 dto 확장포문 -> 리스트하나씩 뽑아내서 
		for(CommuDTO dto : list) {//글 하나씩 뽑아서 dto에 담기
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			dto.setPhoto(commuService.commuSelectPhotoList(dto.getLno()));
		}
		
		return list;
	}
	
	//댓글 하나 가져오는용
	@GetMapping("/gram/comment/selectOne/{cno}")
	public CommuCommentDTO commuCommentSelectOne(@PathVariable String cno) {
		return commuService.commuCommentSelectOne(cno);
	}
	
	//생성용
	@PostMapping("/gram/edit")
	public Map commuInsert(@RequestBody Map map) {
		int affected=commuService.commuInsert(map);
		//affected가 1이면 넘어왔으면 result라는 키로 true반환 아니면 false반환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}
	
		

}
