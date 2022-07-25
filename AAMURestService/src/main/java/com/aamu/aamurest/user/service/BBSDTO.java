package com.aamu.aamurest.user.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BBSDTO {
	private int rbn;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private int themeid;
	private String themename;
	private String id; //작성자
	private List<String> photo;
	private long starttime; //출발시간
	private List<RouteDTO> routeList;
	private List<ReviewDTO> reviewList;
	private double rateavg; //평점 등록
}
