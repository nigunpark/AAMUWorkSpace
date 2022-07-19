package com.aamu.aamurest.user.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.PlannerDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.RouteDTO;

@Service
public class BBSServiceImpl implements BBSService{
	
	@Autowired
	private BBSDAO dao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	//글 목록
	@Override
	public List<BBSDTO> bbsSelectList(Map map) {
		return dao.bbsSelectList(map);
	}
	
	//글 목록_사진 뿌려주기
	@Override
	public List bbsSelectPhotoList(int rbn) {
		return dao.bbsSelectPhotoList(rbn);
	}
	
	//글 상세보기
	@Override
	public BBSDTO bbsSelectOne(int rbn) {
		return dao.bbsSelectOne(rbn);
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
		return dao.bbsDelete(map);
	}
	
	/*---------------------------------------------------*/
	
	//글 상세보기_모든 리뷰 보기
	@Override
	public List<ReviewDTO> reviewList(int rbn) {
		return dao.reviewList(rbn);
	}
	
	//리뷰 등록
	@Override
	public int reviewInsert(Map map) {
		return dao.reviewInsert(map);
	}
	
	//리뷰 수정
	@Override
	public int reviewUpdate(Map map) {
		return dao.reviewUpdate(map);
	}
	
	//리뷰 삭제
	@Override
	public int reviewDelete(Map map) {
		return dao.reviewDelete(map);
	}
	
	//평점 반영
	@Override
	public int ratingInsert(Map map) {
		return dao.ratingInsert(map);
	}
	
	@Override
	//평점 평균 반영
	public int ratingAverageInsert(Map map) {
		return dao.ratingAverageInsert(map);
	}
	
	//테마 등록
	@Override
	public int themeInsert(BBSDTO dto) {
		return dao.themeInsert(dto);
	}

	
}
	