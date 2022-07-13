package com.aamu.aamurest.user.service;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ReviewDTO {
	public int rbn; //
	public String id;
	public int rate; //평점
	public String review;
	public Date ratedate;
	public String rno; // 리뷰 번호
}