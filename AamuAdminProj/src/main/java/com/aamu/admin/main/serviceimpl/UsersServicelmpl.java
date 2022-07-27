package com.aamu.admin.main.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.UsersDTO;
import com.aamu.admin.main.service.UsersService;

@Service
public class UsersServicelmpl implements UsersService{
	@Autowired
	private UsersDAO dao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	//리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 회원 뿌려주기
	@Override
	public ListPagingData<UsersDTO> usersSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.usersGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.usersSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Users.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<UsersDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
				
		return listPagingData;
	}
	
	//전체 회원 수 뿌려주기
	@Override
	public int usersGetTotalRecordCount(Map map) {
		return dao.usersGetTotalRecordCount(map);
	}
	
	//유저 프로필
	@Override
	public String usersSelectUserProf(String id) {
		return dao.usersSelectUserProf(id);
	}
	
	//회원
	@Override
	public int usersStop(Map map) {
		return dao.usersStop(map);
	}
}
