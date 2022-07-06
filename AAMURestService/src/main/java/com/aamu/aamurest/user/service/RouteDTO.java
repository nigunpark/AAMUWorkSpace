package com.aamu.aamurest.user.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RouteDTO {
	private int contentid;
	private int contenttypeid;
	private long starttime;
	private int day;
	private long atime;
	private long mtime;
}
