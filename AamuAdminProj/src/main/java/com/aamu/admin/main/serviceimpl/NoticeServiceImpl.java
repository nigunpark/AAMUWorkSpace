package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.NoticeDTO;
import com.aamu.admin.main.service.NoticeService;
import com.aamu.admin.main.service.PagingUtil;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeDAO dao;

	//리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 글 뿌려주기
	@Override
	public ListPagingData<NoticeDTO> noticeSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.noticeGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.noticeSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Notice.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<NoticeDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 게시물 수 뿌려주기
	@Override
	public int noticeGetTotalRecordCount(Map map) {
		return dao.noticeGetTotalRecordCount(map);
	}
	
	//글 삭제
	@Override
	public int noticeDelete(Map map) {
		int affected=0;
		List<String> lnolists=(List<String>)map.get("lno");
		System.out.println(lnolists);
		for(String lno:lnolists) {
			Map lnoMap = new HashMap(); 
			lnoMap.put("lno", lno); 
			System.out.println(lnoMap);
			affected+=dao.noticeDelete(lnoMap);
		}
		if(affected==((List)map.get("lno")).size()) {
			return 1;
		}
		else
			return 0;
	}

}
