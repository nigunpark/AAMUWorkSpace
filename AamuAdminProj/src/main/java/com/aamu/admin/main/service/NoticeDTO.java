package com.aamu.admin.main.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
	
	/*
		NNO number NOT NULL,
		TITLE nvarchar2(100) NOT NULL,
		CONTENT nvarchar2(2000) NOT NULL,
		NOTICEDATE date DEFAULT SYSDATE NOT NULL,
		NCOUNT number DEFAULT 0,
		PRIMARY KEY (NNO)
	*/
	

	private String nno;
	private String title;
	private String content;
	private java.sql.Date noticedate;
	private String ncount;
	
}