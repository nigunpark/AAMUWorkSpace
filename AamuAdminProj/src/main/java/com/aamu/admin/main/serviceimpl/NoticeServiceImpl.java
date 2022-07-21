package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.NoticeDTO;
import com.aamu.admin.main.service.NoticeService;
import com.aamu.admin.main.service.PagingUtil;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDAO dao;

	// 리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;

	// 전체 글 뿌려주기
	@Override
	public ListPagingData<NoticeDTO> noticeSelectList(Map map, HttpServletRequest req, int nowPage) {
		// 페이징을 위한 로직 시작]
		// 전체 레코드수
		int totalCount = dao.noticeGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		// 페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		// 글 전체 목록 얻기
		List lists = dao.noticeSelectList(map);

		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()),
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()),
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()),
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), req.getContextPath() + "/Notice.do?");

		// Lombok라이브러리 사용시
		ListPagingData<NoticeDTO> listPagingData = ListPagingData.builder().lists(lists).map(map)
				.pagingString(pagingString).build();

		return listPagingData;
	}

	// 전체 게시물 수 뿌려주기
	@Override
	public int noticeGetTotalRecordCount(Map map) {
		return dao.noticeGetTotalRecordCount(map);
	}
	
	// 글 등록
	@Override
	public int noticeWrite(Map map) {
		return dao.noticeWrite(map);
	}
	
	
	@Override
	public int noticeEdit(Map map) throws Exception {		
		return dao.noticeEdit(map);
	}

	
	// 글 상세 보기
	@Override
	public NoticeDTO selectOne(Map map) {
		NoticeDTO record = dao.selectOne(map);
		//줄바꿈 처리
		record.setContent(record.getContent().replace("\r\n","<br/>"));
		return record;
	}
	
	// 조회수
	@Override
	public int noticeCount(Map map) throws Exception {
		return dao.noticeCount(map);
	}

	// 글 삭제
	@Override
	public int noticeDelete(Map map) {
		int affected = 0;
		List<String> nnolists = (List<String>) map.get("nno");
		System.out.println(nnolists);
		for (String nno : nnolists) {
			Map nnoMap = new HashMap();
			nnoMap.put("nno", nno);
			System.out.println(nnoMap);
			affected += dao.noticeDelete(nnoMap);
		}
		if (affected == ((List) map.get("nno")).size()) {
			return 1;
		} else
			return 0;
	}


	public int noticeViewDelete(Map map) {
		
		return dao.noticeViewDelete(map);
			
	}



}
