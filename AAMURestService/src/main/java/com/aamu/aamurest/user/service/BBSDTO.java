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
	public String id; //작성자
	public List<String> photo;
	private int day; //1일차, 2일차 등
	private long starttime; //출발시간
	private long atime; //머문시간
	private long mtime; //이동시간
}
