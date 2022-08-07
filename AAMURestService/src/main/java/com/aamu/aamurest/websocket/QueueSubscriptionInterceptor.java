package com.aamu.aamurest.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import com.aamu.aamurest.user.service.NotificationDTO;

@Component
public class QueueSubscriptionInterceptor extends ChannelInterceptorAdapter {
	
	@Autowired
	private SqlSessionTemplate template;
	
	@Autowired
	private NotificationAlert notificationAlert;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		
		switch (accessor.getCommand()) {
	        case CONNECT:
	            // 유저가 Websocket으로 connect()를 한 뒤 호출됨
	        	System.out.println(accessor.toMap());
	            break;
	        case SUBSCRIBE:
	        	// 구독 했을때 발생
	        	System.out.println(accessor.toMap());
	        	if(accessor.getNativeHeader("destination").get(0).toString().contains("/notification")) {
//	        		String destination = accessor.getNativeHeader("destination").get(0).toString();
//	        		String userid =destination.substring(destination.lastIndexOf("/")+1,destination.length());
//	        		Map map = new HashMap();
//	        		map.put("id", userid);
//	        		List<NotificationDTO> list = template.selectList("selectListNotiMessage", map);
//	        		for(NotificationDTO dto : list) {
//	        			if(dto.getFromthan().equals("BBS")) {
//	        				notificationAlert.serverMessage("이런여행 어때 게시판",dto);
//	        			}
//	        			else {
//	        				notificationAlert.serverMessage("이런곳은 어때 게시판", dto);
//	        			}
//	        		}
	        	}
	        	break;
	        case UNSUBSCRIBE:
	        	// 언구독 했을때 발생
	        	System.out.println(accessor.toMap());
	        	break;
	        case DISCONNECT:
	            // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
	        	System.out.println(accessor.toMap());
	            break;
	        default:
	            break;
	    }
		
		return message;
	}
	
	
	
}
