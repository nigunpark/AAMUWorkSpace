package com.aamu.aamurest.user.service.admin;

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

	private String nno;
	private String title;
	private String content;
	private java.sql.Date noticedate;
	private String ncount;
	private String id;
	private String name; // 이름 저장

}