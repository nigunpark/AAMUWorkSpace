package com.aamu.aamurest.user.service.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

	private String ano;
	private String qno;
	private String id;
	private String answer;
	private java.sql.Date adate;
	private String name; // 이름 저장
	
}