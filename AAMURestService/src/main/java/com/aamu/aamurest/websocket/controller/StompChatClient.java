package com.aamu.aamurest.websocket.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StompChatClient {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat/enter")
	public void enter(Map map) {
		messagingTemplate.convertAndSend("/sub/chat/room", map);
	}
	
	@MessageMapping("/chat/message")
	public void message(Map map) {
		messagingTemplate.convertAndSend("/sub/chat/room", map);
	}
}
