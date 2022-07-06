package com.aamu.aamurest.user.service;

import java.util.Date;


import lombok.Getter;
import lombok.Setter;

public class ReviewDTO {
	private String id;
	private double rate;
	private String review;
	private Date ratedate;
	private int number;
}