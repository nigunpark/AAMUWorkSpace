package com.aamu.admin.main.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.MainService;
import com.aamu.admin.main.service.api.AttractionDTO;

@Service
public class MainServiceImpl implements MainService {

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

		map.put("places", "places");
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
		for (int i = 6; i >= 0; i--) {
			String day = "sysdate-" + i;
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
			String days = "\"" + dao.selectDate(day) + "\"";

			users.add(userCount);
			date.add(days);
			join.add(countJoin);
			commu.add(countCommu);
			bbs.add(countBBS);
			planner.add(countPlanner);
		}

		map.put("userWeek", users);
		map.put("date", date);
		map.put("join", join);
		map.put("commu", commu);
		map.put("bbs", bbs);
		map.put("planner", planner);
		return map;
	}

	@Override
	public Map<String, List> selectStartEnd(Map map) {
		Calendar cal = Calendar.getInstance();

		String start = map.get("start").toString().split("T15")[0];
		String end = map.get("end").toString().split("T15")[0];
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateList = new Vector<>();
		List<Integer> countList = new Vector<>();
		List<Integer> commuList = new Vector<>();
		List<Integer> bbsList = new Vector<>();
		List<Integer> planList = new Vector<>();
		List<Integer> usersList = new Vector<>();
		try {
			Date startday = formatter.parse(start);
			Date endday = formatter.parse(end);
			cal.setTime(endday);
			cal.add(Calendar.DATE, 2);
			end = formatter.format(cal.getTime());

			cal.setTime(startday);
			cal.add(Calendar.DATE, 1);
			start = formatter.format(cal.getTime());
			// startday = formatter.parse(start);
			int countUser = 0;
			int countBBS = 0;
			int countCommu = 0;
			int countPlan = 0;
			int usersCount = 0;
			while (!start.equals(end)) {
				System.out.println("보내는 날짜:" + start);
				map.put("table", "users");
				map.put("column", "joindate");
				// map.put("day", startday);
				map.put("day", start);
				countUser = dao.selectWeekToString(map);
				map.put("table", "COMMUNITY");
				map.put("column", "postdate");
				countCommu = dao.selectWeekToString(map);
				map.put("table", "routebbs");
				countBBS = dao.selectWeekToString(map);
				map.put("table", "routeboard");
				map.put("column", "routedate");
				countPlan = dao.selectWeekToString(map);
				usersCount = dao.selectUsersToString(start);
				usersList.add(usersCount);
				countList.add(countUser);
				commuList.add(countCommu);
				bbsList.add(countBBS);
				planList.add(countPlan);
				start = start.substring(5);
				dateList.add(start);
				cal.add(Calendar.DATE, 1);
				start = formatter.format(cal.getTime());
				// startday = formatter.parse(start);

				System.out.println("세팅된 날짜:" + start);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("countList", countList);
		map.put("dateList", dateList);
		map.put("commuList", commuList);
		map.put("bbsList", bbsList);
		map.put("planList", planList);
		map.put("usersList", usersList);

		return map;
	}

	@Override
	public int placeInsert(AttractionDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkPlace(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

}
