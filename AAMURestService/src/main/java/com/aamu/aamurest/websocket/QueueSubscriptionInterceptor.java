package com.aamu.aamurest.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class QueueSubscriptionInterceptor extends ChannelInterceptorAdapter {

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
