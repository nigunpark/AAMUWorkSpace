package com.aamu.aamurest.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.serviceimpl.BBSServiceImpl;

@CrossOrigin(origins="*")
@RestController
public class BBSController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BBSServiceImpl bbsService;
	
	//글 목록용
	@GetMapping("/bbs/SelectList")
	public List<BBSDTO> bbsSelectList(){
		List<BBSDTO> list = bbsService.bbsSelectList();
		for(BBSDTO dto : list) {
			dto.setPhoto(bbsService.bbsSelectPhotoList(dto.getRbn()));
		}
		return list;
	}
	
	
	
	
	
}