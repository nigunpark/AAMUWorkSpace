package com.aamu.aamurest.user.service;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatingMessageDTO {
	private int roomno;
	private String authid;
	private String missage;
	private Date senddate;
	private String authpro; 
}
