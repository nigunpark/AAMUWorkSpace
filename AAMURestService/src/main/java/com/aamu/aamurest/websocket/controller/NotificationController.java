package com.aamu.aamurest.websocket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.NotificationDTO;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private SqlSessionTemplate template;
	
	
	@GetMapping("/list")
	public List<NotificationDTO> notificationlist(@RequestParam Map map) {
		return template.selectList("selectListNotiMessage", map);
	}
	
	
	@PostMapping("/firebase")
	public Map firebase(@RequestBody Map map) {
		try {
			template.insert("insertfirebase", map);
		}
		catch (Exception e) {
			template.delete("deletefirebase", map);
			template.insert("insertfirebase", map);
		}
		Map returnMap = new HashMap<>();
		returnMap.put("isok", "ok");
		return returnMap;
	}
	
	@DeleteMapping("/firebase")
	public Map delfirebase(@RequestParam Map map) {
		
		template.delete("deletefirebase", map);
		
		Map returnMap = new HashMap<>();
		returnMap.put("isok", "ok");
		return returnMap;
	}
	
}
