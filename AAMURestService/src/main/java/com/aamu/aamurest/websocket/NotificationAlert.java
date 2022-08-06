package com.aamu.aamurest.websocket;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.aamu.aamurest.user.service.NotificationDTO;

public class NotificationAlert {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private SqlSessionTemplate template;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void NotiMessage(String title,String fromthan,NotificationDTO message) {
		
		template.insert("insertNotiMessage", message);
		
		try {
			String firebaseid = template.selectOne("selectonefirebase",message.getId());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer AAAAuSENOMk:APA91bGjt_r6LEeCNsglpl2mNyK99X9WjhatB8iIUBLLHxdNwdy6n8m6idiuPoSLKT79l4GLedIZ0B628RWxiGQgvrUi8CH0ocqDxxb5kUW-G1Yd7urOkjtpBnwfrWOsgVikWVquzbBO");
			
			Map<String,String> notification = new HashMap<>();
			notification.put("title", title);
			notification.put("body", message.getAmessage());
			//데이타 메시지- 자바코드로 하드코딩(혹은 웹 UI만들어서 받던지)
			Map<String,String> data = new HashMap<>();
			data.put("fromthan", fromthan);
			
			Map<String,Object> bodies = new HashMap<>();
			bodies.put("notification", notification);
			bodies.put("data", data);
			bodies.put("to", firebaseid);
			
			//3.HttpEntity객체 생성
			HttpEntity entity = new HttpEntity(bodies, headers);
			//4.RestTemplate으로 요청 보내기
			String url="https://fcm.googleapis.com/fcm/send";
			//한글 포함시
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
			
			ResponseEntity<Map> resultMap = restTemplate.exchange(uriComponents.toString(),
								HttpMethod.POST,
								entity,//HttpEntity(요청바디와 요청헤더가 설정된 HttpEntity타입)
								Map.class);//응답 데이타 타입
			
		}
		catch(NullPointerException e) {}
		
		messagingTemplate.convertAndSend("/queue/notification/"+message.getId(), message);
	}
	
}
