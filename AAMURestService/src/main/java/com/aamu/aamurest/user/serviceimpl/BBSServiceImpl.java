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

@Service
public class BBSServiceImpl implements BBSService{

	@Autowired
	private BBSDAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private MainDAO mainDao;

	//글 목록
	@Override
	public List<BBSDTO> bbsSelectList() {
		List<BBSDTO> bbsList = dao.bbsSelectList();
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
		return dto;
	}

	//글 등록
	@Override
	public int bbsInsert(Map map) {

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
	/*transaction 처리중
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
		return dao.bbsUpdate(map);
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
			map.put("table", "routebbs");
			return dao.bbsDelete(map);
		});
		return affected;
	}

	/*---------------------------------------------------*/
	
	/*
	@Override
	public double setRate(int rbn) {
		Double rateAvg = BBSService.getRateAvg(rbn);
		
		if(rateAvg == null) {
			rateAvg = 0.0;
		}
		
		rateAvg = (double) (Math.round(rateAvg*10));
		rateAvg = rateAvg/10;
		
		ReviewDTO dto = new ReviewDTO();
		dto.setRbn(rbn);
		dto.setRateAvg(rateAvg);
		
		BBSService.updateRateAvg(dto);
		
		return dao.setRate(rbn);
	} */
	
	//리뷰 목록
	@Override
	public List<ReviewDTO> reviewList(int rbn) {
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
	
	//평균 평점
	
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
	
	//검색 결과
	@Override
	public List<BBSDTO> searchList(Map map) {
		return dao.searchList(map);
	}
	/*
	//글 북마크
	@Override
	public Boolean bookmark(Map map) {
		return dao.bookmark(map);
	}
	
	//글_북마크 여부	
	@Override
	public Boolean bookmark(Map map) {
		int isLikeaffected=dao.commuLikeSelect(map);
		if(isLikeaffected == 1) return true;
		else return false;
	}
	*/
	
}
