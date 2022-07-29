package com.aamu.aamurest.websocket.controller;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.ChatingMessageDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StompChatClient {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private SqlSessionTemplate template;
	
	@MessageMapping("/chat/message")
	public void message(ChatingMessageDTO message) {
		template.insert("message",message);
		message.setAuthpro(template.selectOne("messagepro",message));
		messagingTemplate.convertAndSend("/queue/chat/message/"+message.getRoomno(), message);
	}
}
