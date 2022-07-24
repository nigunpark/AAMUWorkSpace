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
public class QNADTO {
	
	/*	
	QNO number NOT NULL, 
	ID varchar2(20) NOT NULL,
	TITLE nvarchar2(100) NOT NULL,
	CONTENT nvarchar2(2000) NOT NULL,
	QCOUNT number DEFAULT 0,
	QDATE date DEFAULT SYSDATE,
	PRIMARY KEY (QNO)
	 */

	private String qno;
	private String id;
	private String title;
	private String content;
	private String qcount;
	private java.sql.Date qdate;

}