package com.aamu.aamurest.user.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuDTO {
	public String lno;
	public String id;
	public String title;
	public String content;
	public Date postdate;
	public String likeCount;
}