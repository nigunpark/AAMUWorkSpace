package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.AdminCommuService;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;

@Service
public class AdminCommuServiceImpl implements AdminCommuService {
	
	@Autowired
	private AdminCommuDAO dao;
	
	//리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 글 뿌려주기
	@Override
	public ListPagingData<AdminCommuDTO> commuSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.commuGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.commuSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Commu.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<AdminCommuDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 게시물 수 뿌려주기
	@Override
	public int commuGetTotalRecordCount(Map map) {
		return dao.commuGetTotalRecordCount(map);
	}
	
	//글 삭제
	@Override
	public int commuDelete(Map map) {
		int affected=0;
		List<String> lnolists=(List<String>)map.get("lno");
		System.out.println(lnolists);
		for(String lno:lnolists) {
			Map lnoMap = new HashMap(); 
			lnoMap.put("lno", lno); 
			System.out.println(lnoMap);
			affected+=dao.commuDelete(lnoMap);
		}
		if(affected==((List)map.get("lno")).size()) {
			return 1;
		}
		else
			return 0;
	}
	
	//댓글 뿌려주기
	@Override
	public ListPagingData<AdminCommuCommentDTO> commuCommentList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.commuCommentGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.commuCommentSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Commu.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<AdminCommuCommentDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 댓글 수 뿌려주기
	@Override
	public int commuCommentGetTotalRecordCount(Map map) {
		return dao.commuCommentGetTotalRecordCount(map);
	}
	
	
	
	

}
