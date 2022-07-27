package com.aamu.admin.main.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BBSAdminDTO {
	private int rbn;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private String id;
}
