package com.aamu.aamurest.user.service;

import java.util.Date;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlannerDTO {
	private String title;
	private int rbn;
	private List<RouteDTO> route=null;
	private String id;
	private Date routeDate;
	private String plannerDate;
	
	
}
