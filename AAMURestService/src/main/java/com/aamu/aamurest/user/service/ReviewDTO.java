package com.aamu.aamurest.user.service;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
	public int rbn;
	public String id;
	public int rate; //평점
	public String review;
	public Date ratedate;
	public String rno;
	public void setReview(String reviewSelectOne) {
		// TODO Auto-generated method stub
		
	}
		
}