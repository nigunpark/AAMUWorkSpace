package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.admin.main.service.BBSAdminDTO;
import com.aamu.admin.main.service.BBSAdminService;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.ReviewAdminDTO;

@Service
public class BBSAdminServiceImpl implements BBSAdminService{
	
	@Autowired
	private BBSAdminDAO dao;
	
	@Autowired
	private UsersDAO usersDao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	// 리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 게시글 뿌려주기
	@Override
	public ListPagingData<BBSAdminDTO> bbsAdminSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.bbsGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//게시글 전체 목록 얻기
		List lists= dao.bbsSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
			Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
			Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
			Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
			Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
			req.getContextPath()+"/bbs.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<BBSAdminDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 게시글 수
	@Override
	public int bbsGetTotalRecordCount(Map map) {
		return dao.bbsGetTotalRecordCount(map);
	}
	
	//게시글 삭제
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
	
	//리뷰 목록
	@Override
	public ListPagingData<ReviewAdminDTO> reviewAdminSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.reviewGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//게시글 전체 목록 얻기
		List lists= dao.reviewAdminSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
			Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
			Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
			Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
			Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
			req.getContextPath()+"/bbs.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<ReviewAdminDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 리뷰 수
	@Override
	public int reviewGetTotalRecordCount(Map map) {
		return dao.reviewGetTotalRecordCount(map);
	}
	
	//리뷰 삭제
	@Override
	public int reviewAdminDelete(Map map) {
		int affected=0;
		List<String> rnolists=(List<String>)map.get("rno");
		System.out.println(rnolists);
		for (String rno : rnolists) {
			Map rnoMap = new HashMap();
			rnoMap.put("rno", rno);
			System.out.println(rnoMap);
			affected += dao.reviewDelete(rnoMap);
		}
		if (affected == ((List) map.get("rno")).size()) {
				return 1;
			} else
				return 0;
		}
	
	/*---------------------------------------------------------------*/
	/*
	//게시판 통계
	@Override
	public Map bbsTotal() {
		Map map = new HashMap<>();
		//월별
		map.put("bbsMonthTotal", dao.bbsMonthTotal(map));
		
		return map;
	}
	
	//게시판 통계_베스트 게시글
	@Override
	public List<BBSAdminDTO> bbsBestList() {
		//베스트 글 
		List<BBSAdminDTO> lists=dao.bbsBestList();
		return lists;
	}
	
	//게시판 통계_프로필 뿌려주기
	@Override
	public String bbsSelectProfile(String id) {
		return dao.bbsSelectProfile(id);
	}
	*/
		
}
	
	
	

