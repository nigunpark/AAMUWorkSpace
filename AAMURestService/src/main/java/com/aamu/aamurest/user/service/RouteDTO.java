package com.aamu.aamurest.user.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDTO {
	private int contentid;
	private int contenttypeid;
	private long startTime;
	private int day;
	private long atime;
	private long mtime;
}
