package com.aamu.aamurest.user.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		List<CommuDTO> list = commuService.commuSelectList();
		return list;
	}
	
	//생성용
	@PostMapping("/gram/edit")
	public Map commuInsert(@RequestBody Map map) {
		int affected=commuService.commuInsert(map);
		return map;
	}
	
		

}
