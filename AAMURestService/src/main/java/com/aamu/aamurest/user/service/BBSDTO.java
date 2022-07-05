package com.aamu.aamurest.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BBSDTO {
	public String rbn;
	public String title;
	public String content;
	public Date postdate;
	public String themeid;
	public List<Map> photo;
}
