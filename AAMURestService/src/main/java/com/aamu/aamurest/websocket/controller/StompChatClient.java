package com.aamu.aamurest.websocket.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.user.service.ChatingMessageDTO;
import com.aamu.aamurest.util.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@RestController
public class StompChatClient {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private SqlSessionTemplate template;
	
	@MessageMapping("/chat/message")
	public void message(ChatingMessageDTO message) {
		template.insert("message",message);
		template.update("updatelastmsg", message);
		//message.setAuthpro(FileUploadUtil.requestOneFile(template.selectOne("messagepro",message), "/resources/userUpload", req));
		messagingTemplate.convertAndSend("/queue/chat/message/"+message.getRoomno(), message);
	}
}
