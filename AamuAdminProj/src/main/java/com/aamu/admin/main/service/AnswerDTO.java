package com.aamu.admin.main.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
	
	/*
	ANO number NOT NULL,
	QNO number NOT NULL,
	ID varchar2(20) NOT NULL,
	ANSWER nvarchar2(2000) NOT NULL,
	ADATE date DEFAULT SYSDATE,
	PRIMARY KEY (ANO)
	*/
	
	private String ano;
	private String qno;
	private String id;
	private String answer;
	private java.sql.Date adate;
	private String name;//이름 저장용
	
}