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
import com.aamu.admin.main.service.ThemeDTO;
import com.aamu.admin.main.service.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService{
	
	@Autowired
	private ThemeDAO dao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	//리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	@Override
	public ListPagingData<ThemeDTO> themeSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		//int totalCount=dao.themeSelectList(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		//map.put(PagingUtil.TOTAL_COUNT, totalCount);
		
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List<ThemeDTO> lists= dao.themeSelectList(map);
		
		
		return lists;
	}

}
