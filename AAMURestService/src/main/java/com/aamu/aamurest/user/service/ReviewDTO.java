package com.aamu.aamurest.user.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private int rbn;
	private String id; //리뷰 작성자
	private double rate; //평점
	private String review;
	private java.sql.Date ratedate; //리뷰 작성날짜
	private int rno;
	
}