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
	public int rbn;
	public String title;
	public String content;
	public java.sql.Date postdate;
	public int themeid;
	public String themename;
	public double rateavg; //평균 평점
	public List<String> photo;
	public String id; //작성자 id
	private int day; //1일차, 2일차 등
	private long starttime;
	private long atime; //머문시간
	private long mtime; //이동시간
}
