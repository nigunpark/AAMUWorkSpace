package com.aamu.aamurest.user.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDTO {
	private int areacode;
	private int sigungucode;
	private int contentid;
	private int contenttypeid;
	private double mapx;
	private double mapy;
	private String title;
	private String addr;
	private String bigImage;
	private String smallImage;
	private String playtime;
	private String tel;
	private String resttime;
	private String checkin;
	private String checkout;
	private String url;
	private String kakaokey;
	private String menu;
	private String park;
	private String charge;
	private String eventstart;
	private String eventend;
	private String eventTime;
}
