package com.aamu.aamurest.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private String plannerdate;
	private Map<String,List<RouteDTO>> routeMap=null;
	private String smallImage;

}
