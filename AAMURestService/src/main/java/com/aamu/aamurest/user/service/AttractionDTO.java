package com.aamu.aamurest.user.service;

import java.util.List;

import com.aamu.aamurest.user.service.api.KakaoReview.CommentInfo;

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
	private String bigimage;
	private String smallimage;
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
	private String eventtime;
	private long atime = 2*1000*60*60;
	private String table;
	private double star;
	public List<CommentInfo> commentInfo=null;
}
