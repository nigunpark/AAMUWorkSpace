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
	public int rbn;
	public String id; //리뷰 작성자
	public int rate; //평점
	public String review;
	public java.sql.Date ratedate; //리뷰 작성날짜
	public String rno;
}