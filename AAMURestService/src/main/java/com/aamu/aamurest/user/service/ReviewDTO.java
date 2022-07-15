package com.aamu.aamurest.user.service;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
	public int rbn;
	public String id; //리뷰 작성자
	public int rate; //평점
	public String review;
	public Date ratedate; //리뷰 작성날짜
	public String rno;		
}