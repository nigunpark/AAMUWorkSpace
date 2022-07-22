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
public class AdminCommuDTO {
	private String lno;
	private String ctitle;
	private String id;
	private java.sql.Date postdate;
	private String rcount;
	private String likecount;
	private String userprofile;
	private java.sql.Date joindate;
}
