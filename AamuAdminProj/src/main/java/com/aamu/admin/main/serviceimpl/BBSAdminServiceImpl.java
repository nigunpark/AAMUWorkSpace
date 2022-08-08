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
	private TransactionTemplate transactionTemplate;
	
	// 리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 게시글 뿌려주기
	@Override
	public ListPagingData<BBSAdminDTO> bbsSelectList(Map map, HttpServletRequest req, int nowPage) {
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
		int affected=0;
		List<String> rbnlists=(List<String>)map.get("rbn");
		System.out.println(rbnlists);
		for (String rbn : rbnlists) {
			Map rbnMap = new HashMap();
			rbnMap.put("rbn", rbn);
			System.out.println(rbnMap);
			affected += dao.reviewDelete(rbnMap);
		}
		if (affected == ((List) map.get("rbn")).size()) {
			return 1;
		} else
			return 0;
	}
	
	//리뷰 목록
	@Override
	public ListPagingData<ReviewAdminDTO> reviewSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.reviewGetTotalRecordCount(map);
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
	public int reviewDelete(Map map) {
		int affected=0;
		List<String> rbnlists=(List<String>)map.get("rbn");
		System.out.println(rbnlists);
		for (String rbn : rbnlists) {
			Map rbnMap = new HashMap();
			rbnMap.put("rbn", rbn);
			System.out.println(rbnMap);
			affected += dao.reviewDelete(rbnMap);
		}
		if (affected == ((List) map.get("rbn")).size()) {
				return 1;
			} else
				return 0;
		}
		
}
	
	
	

