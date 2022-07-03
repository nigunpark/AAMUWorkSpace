package com.aamu.aamurest.user.service;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlannerDTO {
	private String title;
	private Date startTime;
	private int rbn;
	private List<Route> route=null;
	private String id;
	
	@Getter
	@Setter
	public static class Route{
		private int contentid;
		private int day;
		private long atime;
		private long mtime;
	}
}
