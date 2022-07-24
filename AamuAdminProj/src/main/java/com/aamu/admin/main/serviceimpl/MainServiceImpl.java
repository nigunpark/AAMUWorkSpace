package com.aamu.admin.main.serviceimpl;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.MainService;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.api.AreaCountDTO;
import com.aamu.admin.main.service.api.AttractionDTO;

@Service
@PropertySource("classpath:admin/resources/paging.properties")
public class MainServiceImpl implements MainService {

	@Autowired
	private MainDAO dao;
	
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;

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

	@Override
	public ListPagingData<AreaCountDTO> countAllPlaces(Map pagingMap, HttpServletRequest req, int nowPage) {
		Map map = new HashMap();
		List list = new Vector<>();
		String area =null;
		int areacode = 0;
		
		for(int i=1;i<=17;i++) {
			areacode = i;
			if(i>8) areacode +=22;
			switch(areacode) {
			case 1:
				area = "서울";
				count(areacode, map, list,area);
				break;
			case 2:
				area = "인천";
				count(areacode, map, list,area);
				break;
			case 3:
				area = "대전";
				count(areacode, map, list,area);
				break;
			case 4:
				area = "대구";
				count(areacode, map, list,area);
				break;
			case 5:
				area = "광주";
				count(areacode, map, list,area);
				break;
			case 6:
				area = "부산";
				count(areacode, map, list,area);
				break;
			case 7:
				area = "울산";
				count(areacode, map, list,area);
				break;
			case 31:
				area = "경기도";
				count(areacode, map, list,area);
				break;
			case 32:
				area = "강원도";
				count(areacode, map, list,area);
				break;
			case 33:
				area = "충청북도";
				count(areacode, map, list,area);
				break;
			case 34:
				area = "충청남도";
				count(areacode, map, list,area);
				break;
			case 35:
				area = "경상북도";
				count(areacode, map, list,area);
				break;
			case 36:
				area = "경상남도";
				count(areacode, map, list,area);
				break;
			case 37:
				area = "전라북도";
				count(areacode, map, list,area);
				break;
			case 38:
				area = "전라남도";
				count(areacode, map, list,area);
				break;
			case 39:
				area = "제주도";
				count(areacode, map, list,area);
				break;
			
		}
	}
		
		int totalCount=list.size();
		pagingMap.put(PagingUtil.PAGE_SIZE, pageSize);
		pagingMap.put(PagingUtil.BLOCK_PAGE, blockPage);
		pagingMap.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(pagingMap);
		//글 전체 목록 얻기;
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(pagingMap.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(pagingMap.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(pagingMap.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(pagingMap.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/adminbackup.do?");
		
		//Lombok라이브러리 사용시
		List pagingList = new Vector<>();
		int totalPage= (int)(Math.ceil(((double)totalCount/pageSize)));
		int i=(nowPage-1)*totalPage;
		for(int k=0;k<pageSize;k++) {
			pagingList.add(list.get(i+k));
		}

		ListPagingData<AreaCountDTO> listPagingData =ListPagingData.builder().lists(pagingList).map(pagingMap).pagingString(pagingString).build();
		
		return listPagingData;
	}
	public List<AreaCountDTO> count(int i,Map map,List<AreaCountDTO> list,String area) {
		for(int k=0;k<4;k++) {
			AreaCountDTO dto = new AreaCountDTO();
			dto.setArea(area);
			switch(k) {
			case 0:
				dto.setContenttype("관광지");
				map.put("areacode", i);
				map.put("contenttypeid", 12);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 1:
				dto.setContenttype("호텔");
				map.put("areacode", i);
				map.put("contenttypeid", 32);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 2:
				dto.setContenttype("식당");
				map.put("areacode", i);
				map.put("contenttypeid", 39);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 3:
				dto.setContenttype("행사");
				map.put("areacode", i);
				map.put("contenttypeid", 15);
				dto.setCount(dao.countAllPlaces(map));
				break;
				}
			list.add(dto);
			}
		return list;
	}
	public List<AreaCountDTO> searchPlaces(int i,Map map,List<AreaCountDTO> list,String area) {
		for(int k=0;k<4;k++) {
			AreaCountDTO dto = new AreaCountDTO();
			dto.setArea(area);
			switch(k) {
			case 0:
				dto.setContenttype("관광지");
				map.put("areacode", i);
				map.put("contenttypeid", 12);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 1:
				dto.setContenttype("호텔");
				map.put("areacode", i);
				map.put("contenttypeid", 32);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 2:
				dto.setContenttype("식당");
				map.put("areacode", i);
				map.put("contenttypeid", 39);
				dto.setCount(dao.countAllPlaces(map));
				break;
			case 3:
				dto.setContenttype("행사");
				map.put("areacode", i);
				map.put("contenttypeid", 15);
				dto.setCount(dao.countAllPlaces(map));
				break;
				}
			list.add(dto);
			}
		return list;
	}
	public static Map switchArea(String area,String contenttype) {
		Map codeMap = new HashMap<>();
		int areacode=0;
		int contenttypeid=0;
		switch(area) {
			case "서울":
				areacode=1;
				break;
			case "인천":
				areacode=2;
				break;
			case "대전":
				areacode=3;
				break;
			case "대구":
				areacode=4;
				break;
			case "광주":
				areacode=5;
				break;
			case "부산":
				areacode=6;
				break;
			case "울산":
				areacode=7;
				break;
			case "세종":
				areacode=8;
				break;
			case "경기도":
				areacode=31;
				break;
			case "강원도":
				areacode=32;
				break;
			case "충청북도":
				areacode=33;
				break;
			case "충청남도":
				areacode=34;
				break;
			case "경상북도":
				areacode=35;
				break;
			case "경상암도":
				areacode=36;
				break;
			case "전라북도":
				areacode=37;
				break;
			case "전라남도":
				areacode=38;
				break;
			case "제주도":
				areacode=39;
				break;
			}
		switch(contenttype) {
			case"관광지":
				contenttypeid=12;
				break;
			case"호텔":
				contenttypeid=32;
				break;
			case"식당":
				contenttypeid=39;
				break;
			case"행사":
				contenttypeid=15;
				break;
			case"레져":
				contenttypeid=28;
				break;	
		}
		codeMap.put("areacode", areacode);
		codeMap.put("contenttypeid", contenttypeid);
		
		return codeMap;
		
	}

}
