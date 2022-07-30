package com.aamu.admin.main.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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