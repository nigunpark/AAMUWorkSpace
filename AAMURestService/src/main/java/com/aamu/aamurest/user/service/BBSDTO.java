package com.aamu.aamurest.user.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BBSDTO {
	private int rbn;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private int themeid;
	private String themename; //테마 이름
	private String themeimg; //테마 이미지
	private String id; //작성자
	private List<String> photo;
	private long starttime; //출발시간
	private PlannerDTO planner; 
	private List<RouteDTO> routeList;
	private List<ReviewDTO> reviewList;
	private double rateavg; //평균 평점
	private boolean bookmark; //북마크
	private List<Map> themeList;
}
