package com.aamu.admin.main.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAdminDTO {
	private int rbn;
	private String id;
	private int rate;
	private String review;
	private java.sql.Date ratedate; 
	private String rno;
}
