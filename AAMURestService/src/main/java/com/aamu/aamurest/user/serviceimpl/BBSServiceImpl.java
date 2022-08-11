package com.aamu.aamurest.user.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.AttractionDTO;
import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.RouteDTO;
import com.aamu.aamurest.util.UserUtil;

@Service
public class BBSServiceImpl implements BBSService{

	@Autowired
	private BBSDAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private MainDAO mainDao;
	
	@Autowired
	private UsersDAO usersDao;

	//글 목록
	@Override
	public List<BBSDTO> bbsSelectList(Map map) {
		List<BBSDTO> bbsList = dao.bbsSelectList(map);
		List<BBSDTO> returnList = new Vector<>();

		for(BBSDTO dto:bbsList) {
			int rbn = dto.getRbn();
			dto.setReviewList(dao.reviewSelectList(rbn));
			returnList.add(dto);
		}
		
		return returnList;
	}

	//글 목록_사진 뿌려주기
	@Override
	public List bbsSelectPhotoList(int rbn) {
		return dao.bbsSelectPhotoList(rbn);
	}
	
	//글 상세보기
	@Override
	public BBSDTO bbsSelectOne(int rbn) {
		BBSDTO dto = dao.bbsSelectOne(rbn);
		List<RouteDTO>routes = dao.selectRouteList(rbn);
		for(RouteDTO route:routes) {
			route.setDto(mainDao.selectOnePlace(route.getContentid()));
		}
		dto.setRouteList(routes);
		dto.setPlanner(mainDao.selectPlannerOne(rbn));
		dto.setThemeList(usersDao.selectAllTheme());
		return dto;
	}
	
	//글 북마크하기
	@Override
	public boolean bbsBookmark(Map map) {
	int bbsSelectBookmarkAffected=dao.bbsSelectBookmark(map);
	if(bbsSelectBookmarkAffected==0) {
		System.out.println("북마크를 성공");
		if(dao.bbsBookmarkInsert(map)==1) return true;
		else return false;
	}
	else { //다시 누를 경우 == 북마크 취소
		System.out.println("북마크를 삭제");
		if(dao.bbsBookmarkDelete(map)==1) return false;
		else return true;
		}
	}
	
	//북마크 여부 확인
	@Override
	public boolean checkBookmark(Map map) {
		int affected = dao.bbsSelectBookmark(map);
		boolean isBookmark = false;
		if(affected==1) isBookmark =true;
		return isBookmark;
	}

	//글 북마크 목록
	@Override
	public List<BBSDTO> bbsBookmarkList(Map map) {
		List<BBSDTO> bookmarkList = dao.bbsBookmarkList(map);
		List<BBSDTO> returnList = new Vector<>();
		for(BBSDTO dto:bookmarkList) {	
			int rbn = dto.getRbn();
			dto.setReviewList(dao.reviewSelectList(rbn));
			returnList.add(dto);
		}
		return returnList;
	}


	//글 등록
	@Override
	public int bbsInsert(Map map) {
		String themename = map.get("themeid").toString();
		map.put("themeid",mainDao.getThemeid(themename));
		int bbsaffected=dao.bbsInsert(map);
		//사진
		int photoAffected=0;
		List<String> lists= (List<String>) map.get("photolist");

		System.out.println("(bbsServiceImpl)lists:"+lists);
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("rbn", map.get("rbn"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
			System.out.println("(CommuServiceImpl)photoAffected:"+photoAffected);
		}
		if(bbsaffected==1 && photoAffected==((List)map.get("photolist")).size())
			return 1;
		else
			return 0;
	}
	//transaction 처리중
	/*
	//테마 등록
	@Override
	public int themeInsert(BBSDTO dto) {
		int affected=0;

		affected = transactionTemplate.execute(tx->{
			int insertTheme = dao.themeInsert(dto);
			List<BBSDTO> themes = dto.themeid();

			for(BBSDTO theme:themes) {
				theme.setRbn(dto.getRbn());
				dao.themeInsert(theme);
			}
			return insertTheme;

		});
		return affected;
	} */

	//글 수정
	@Override
	public int bbsUpdate(Map map) {
		int bbsaffected = 0;
		String themename = map.get("themename").toString();
		map.put("themeid",mainDao.getThemeid(themename));
		bbsaffected = dao.bbsUpdate(map);
		int photoAffected=0;
		if(map.get("photolist")!=null) {
			List<String> lists= (List<String>) map.get("photolist");
			System.out.println("(bbsServiceImpl)lists:"+lists);
			for(String photo:lists) {
				Map photomap = new HashMap();
				photomap.put("rbn", map.get("rbn"));
				photomap.put("photo", photo);
				photoAffected+=dao.photoInsert(photomap);
				System.out.println("(CommuServiceImpl)photoAffected:"+photoAffected);
			}
			if(bbsaffected==1 && photoAffected==((List)map.get("photolist")).size())
				return 1;
			else
				return 0;
		}
		return bbsaffected;
	}

	//글 삭제
	@Override
	public int bbsDelete(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx->{
			map.put("table", "routebbsphoto");
			dao.bbsDelete(map);
			map.put("table", "ratereview");
			dao.bbsDelete(map);
			map.put("table", "bookmark");
			dao.bbsDelete(map);
			map.put("table", "routebbs");
			
			return dao.bbsDelete(map);
		});
		return affected;
	}	
	
	//글 검색 목록
	@Override
	public List<String> bbsSearchList(Map map) {
		if(map.get("searchColumn").equals("id")) {
			map.put("table", "users");
			List<String> list=dao.bbsSearchList(map);
			return list;
		}
		else if(map.get("searchColumn").equals("title")){
			map.put("table", "routebbs");
			return dao.bbsSearchList(map);
		}
		else if(map.get("searchColumn").equals("themename")){
			map.put("table", "theme");
			return dao.bbsSearchList(map);
		}
		return dao.bbsSearchList(map);
	}


	/*---------------------------------------------------*/
	
	//리뷰 목록
	@Override
	public List<ReviewDTO> reviewSelectList(int rbn) {
		return dao.reviewSelectList(rbn);
	}
		
	//리뷰 등록
	@Override
	public int reviewInsert(Map map) {
		return dao.reviewInsert(map);
	}
	
	/*
	//리뷰 수정
	@Override
	public int reviewUpdate(Map map) {
		return dao.reviewUpdate(map);
	}
	*/
	
	//리뷰 삭제
	@Override
	public int reviewDelete(Map map) {
		return dao.reviewDelete(map);
	}
	
	/*---------------------------------------------------*/
	
	//테마 사진 하나 뿌려주기
	public BBSDTO themeSelectOne(Map map) {
		return dao.themeSelectOne(map);
	}

	//평점 업데이트
	@Override
	public int updateRate(Map map) {
		return dao.updateRate(map);
	}

	public List<RouteDTO> selectRouteList(int rbn) {
		return dao.selectRouteList(rbn);
	}

	@Override
	public String bbsSelectUserID(int rbn) {
		return dao.bbsSelectUserID(rbn);
	}

}
	
	

