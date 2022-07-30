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
import com.aamu.admin.main.service.PagingUtil;
import com.aamu.admin.main.service.QNADTO;
import com.aamu.admin.main.service.QNAService;

@Service("qnaService")
public class QNAServiceImpl implements QNAService<QNADTO> {

	@Autowired
	private QNADAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	// 리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;

	// 게시물 목록
	@Override
	public ListPagingData<QNADTO> selectList(Map map, HttpServletRequest req, int nowPage) {
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

	// 게시믈 수 카운트
	@Override
	public int qnaGetTotalRecordCount(Map map) {
		return dao.qnaGetTotalRecordCount(map);
	}

	// 상세 보기
	@Override
	public QNADTO selectOne(Map map) {
		QNADTO record = dao.selectOne(map);
		// 줄바꿈 처리
		record.setContent(record.getContent().replace("\r\n", "<br/>"));
		return record;
	}

	// 게시물 등록
	@Override
	public int qnaWrite(Map map) {
		return dao.qnaWrite(map);
	}

	// 게시물 수정
	@Override
	public int qnaEdit(Map map) throws Exception {
		return dao.qnaEdit(map);
	}


	// 조회 수 카운트
	@Override
	public int qnaCount(Map map) throws Exception {
		return dao.qnaCount(map);
	}
	
	// 이름 찾기
	@Override
	public String findNameByKey(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	// 게시물 번호에 따른 댓글 삭제용 DAO 주입 받기
	@Autowired
	private AnswerDAO adao;

	// 트렌젝션을 활용하여 먼저 댓글 삭제 후 게시물 삭제
	@Override
	public int qnaDelete(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx -> {
			List<String> qnoLists = (List<String>) map.get("qno");

			for (String qno : qnoLists) {
				map.put("table", "answer");
				map.put("qno", qno);
				adao.answerAllDelete(map);
				dao.qnaDelete(map);
				map.put("table", "qna");
			}
			return dao.qnaDelete(map);
		});

		return affected;
	}
	
	// 상세 보기에서 게시물 삭제
	@Override
	public int qnaViewDelete(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx -> {
			int deleteAnswerCount = adao.deleteByNo(map);
			dao.qnaDelete(map);
			return deleteAnswerCount;
		});
		return affected;
	}

	// 댓글 등록
	@Override
	public int answerWrite(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// 댓글 수정
	@Override
	public int answerUpdate(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// 댓글 삭제
	@Override
	public int answerDelete(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 댓글 전부 삭제
	@Override
	public int answerAllDelete(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

}