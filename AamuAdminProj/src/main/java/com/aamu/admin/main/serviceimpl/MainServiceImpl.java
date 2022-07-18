package com.aamu.admin.main.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.MainService;

@Service
public class MainServiceImpl implements MainService{
	
	@Autowired
	private MainDAO dao;
	
	@Override
	public int usersTotalCount() {
		
		return dao.usersTotalCount();
	}

	@Override
	public int usersTodayCount() {
		Date current = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		String today = formatter.format(current);
		return dao.usersTodayCount(today);
	}

	@Override
	public Map placesTotalCount() {
		
		Map map = new HashMap<>();
		
		map.put("places", "placesinfo");
		map.put("attraction", dao.placesTotalCount(map));
		
		map.put("places", "hotelinfo");
		map.put("hotel", dao.placesTotalCount(map));
		
		map.put("places", "dinerinfo");
		map.put("diner", dao.placesTotalCount(map));
		
		map.put("event", dao.selectEvent());
		
		map.put("places", "COMMUNITY");
		map.put("commuCount", dao.placesTotalCount(map));
		
		map.put("places", "routebbs");
		map.put("bbsCount", dao.placesTotalCount(map));
		
		map.put("places", "routeboard");
		map.put("plannerCount", dao.placesTotalCount(map));
		
		map.put("places","places");
		map.put("places", dao.placesTotalCount(map));
		
		return map;
	}

	@Override
	public int selectEvent() {
		
		return dao.selectEvent();
	}



	@Override
	public Map selectWeek() {
		
		Map map = new HashMap<>();
		
		List join = new Vector<>();
		List date = new Vector<>();
		List users = new Vector<>();
		List commu = new Vector<>();
		List bbs = new Vector<>();
		List planner = new Vector<>();
		for(int i=6;i>=0;i--) {
			String day = "sysdate-"+i;
			int userCount = dao.selectUsers(day);
			map.put("table", "users");
			map.put("column", "joindate");
			map.put("day", day);
			int countJoin = dao.selectWeek(map);
			map.put("table", "COMMUNITY");
			map.put("column", "postdate");
			int countCommu = dao.selectWeek(map);
			map.put("table", "routebbs");
			int countBBS = dao.selectWeek(map);
			map.put("table", "routeboard");
			map.put("column", "routedate");
			int countPlanner = dao.selectWeek(map);
			String days =  "\""+dao.selectDate(day)+"\"";
			
			users.add(userCount);
			date.add(days);
			join.add(countJoin);
			commu.add(countCommu);
			bbs.add(countBBS);
			planner.add(countPlanner);
		}

		map.put("userWeek", users);
		map.put("date", date);
		map.put("join",join);
		map.put("commu", commu);
		map.put("bbs", bbs);
		map.put("planner", planner);
		return map;
	}

	@Override
	public List<Integer> selectStartEnd(Map map) {
		int startInt = Integer.parseInt(map.get("start").toString().split("-")[2]);
		int end = Integer.parseInt(map.get("end").toString().split("-")[2]);
		
		startInt+=1;
		end+=1;
		for(int i=0;i<=end;i++) {
			
		}
		return null;
	}
	
	
}
