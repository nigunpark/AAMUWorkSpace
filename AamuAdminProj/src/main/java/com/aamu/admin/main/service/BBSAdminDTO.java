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
public class BBSAdminDTO {
	private String rbn;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private String id;
	private String profile;
	private String bbscount; //총 게시글 수
}
