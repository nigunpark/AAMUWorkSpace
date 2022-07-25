package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.QNADTO;
import com.aamu.admin.main.service.QNAService;

@Service
public class QNAServiceImpl implements QNAService {

	@Autowired
	private QNADAO dao;

	// 리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;

	// 전체 글 뿌려주기
	@Override
	public ListPagingData<QNADTO> qnaSelectList(Map map, HttpServletRequest req, int nowPage) {
		// 페이징을 위한 로직 시작]
		// 전체 레코드수
		int totalCount = dao.qnaGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		// 페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		// 글 전체 목록 얻기
		List lists = dao.qnaSelectList(map);

		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()),
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()),
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()),
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), req.getContextPath() + "/QNA.do?");

		// Lombok라이브러리 사용시
		ListPagingData<QNADTO> listPagingData = ListPagingData.builder().lists(lists).map(map)
				.pagingString(pagingString).build();

		return listPagingData;
	}

	// 전체 게시물 수 뿌려주기
	@Override
	public int qnaGetTotalRecordCount(Map map) {
		return dao.qnaGetTotalRecordCount(map);
	}


	// 글 상세 보기
	@Override
	public QNADTO selectOne(Map map) {
		QNADTO record = dao.selectOne(map);
		// 줄바꿈 처리
		record.setContent(record.getContent().replace("\r\n", "<br/>"));
		return record;
	}

	// 조회수
	@Override
	public int qnaCount(Map map) throws Exception {
		return dao.qnaCount(map);
	}

	// 글 삭제
	@Override
	public int qnaDelete(Map map) {
		int affected = 0;
		List<String> qnolists = (List<String>) map.get("qno");
		System.out.println(qnolists);
		for (String qno : qnolists) {
			Map qnoMap = new HashMap();
			qnoMap.put("qno", qno);
			System.out.println(qnoMap);
			affected += dao.qnaDelete(qnoMap);
		}
		if (affected == ((List) map.get("qno")).size()) {
			return 1;
		} else
			return 0;
	}

	public int qnaViewDelete(Map map) {

		return dao.qnaViewDelete(map);

	}

}