package com.aamu.aamurest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aamu.aamurest.websocket.NotificationAlert;

@Configuration
public class NotificationConfiguration {
	
	@Bean
	public NotificationAlert notificationAlert() {
		return new NotificationAlert();
	}
	
}
