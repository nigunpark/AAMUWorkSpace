package com.aamu.aamurest.user.serviceimpl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.admin.AnswerDTO;
import com.aamu.aamurest.user.service.admin.QNADTO;
import com.aamu.aamurest.user.service.admin.QNAService;

@Service
public class QNAServiceImpl implements QNAService<QNADTO> {

	@Autowired
	private QNADAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	// 게시물 리스트
	@Override
	public List<QNADTO> qnaSelectList(Map map) {
		List<QNADTO> lists = dao.qnaSelectList(map);
		List<QNADTO> returnLists = new Vector();
		for (int i = 0; i < lists.size(); i++) {
			QNADTO dto = lists.get(i);
			map.put("qno", dto.getQno());
			returnLists.add(dto);
		}
		return returnLists;
	}

	// 게시물 수 카운트
	@Override
	public int qnaSearchTotalCount(Map map) {
		return dao.qnaSearchTotalCount(map);
	}

	// 게시물 검색
	@Override
	public List<String> qnaSearachList(Map map) {
		if (map.get("searchColumn").equals("id")) {
			// map.put("searchWord", map.get("searchWord").toString()); 어퍼케이스 하려고 넣었나?
			map.put("table", "users");
			List<String> list = dao.qnaSearachList(map);
			return list;
		} else if (map.get("searchColumn").equals("title")) {
			map.put("table", "qna");
			return dao.qnaSearachList(map);
		} else {// tname이 넘어온거 searchColumn:tname , searchWord:서울
			map.put("table", "tags");
			List<String> nameList = dao.qnaSearachList(map);
			List<String> shrapTnameList = new Vector();
			for (String name : nameList) {
				String sharpTname = "#" + name;
				shrapTnameList.add(sharpTname);
			}
			return shrapTnameList;
		}
	}

	// 상세 보기
	@Override
	public QNADTO qnaSelectOne(Map map) {
		QNADTO dto = dao.qnaSelectOne(map);
		return dto;
	}

	// 게시물 등록
	@Override
	public int qnaInsert(Map map) {

		int qnaaffected = dao.qnaInsert(map);

		if (qnaaffected == 1)
			return 1;
		else
			return 0;
	}

	// 게시물 수정
	@Override
	public int qnaUpdate(Map map) {
		return dao.qnaUpdate(map);
	}

	// 게시물 번호에 따른 댓글 삭제용 DAO 주입 받기
	@Autowired
	private QNADAO adao;

	/*
	 * @Override public int qnaDelete(Map map) { int affected = 0; affected =
	 * transactionTemplate.execute(tx -> { List<String> qnoLists = (List<String>)
	 * map.get("qno");
	 * 
	 * for (String qno : qnoLists) { map.put("table", "answer"); map.put("qno",
	 * qno); adao.answerAllDelete(map); dao.qnaDelete(map); map.put("table", "qna");
	 * } return dao.qnaDelete(map); });
	 * 
	 * return affected; }
	 */

	// 상세 보기에서 게시물 삭제
	@Override
	public int qnaDelete(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx -> {
			map.put("table", "answer");
			adao.answerAllDelete(map);
			map.put("table", "qna");
			int deleteCount = dao.qnaDelete(map);
			return deleteCount;
		});
		return affected;
	}

	/*
	 * map.put("table", "answer"); adao.answerAllDelete(map); map.put("table",
	 * "qna"); dao.qnaDelete(map); return dao.qnaDelete(map);
	 * 
	 * @Override public int qnaDelete(Map map) { return dao.qnaDelete(map); }
	 */

	/*
	 * @Override public int qnaDelete(String qno) { int affected=0; Map map = new
	 * HashMap<>(); affected = transactionTemplate.execute(tx->{ map.put("table",
	 * "answer"); map.put("qno", qno); dao.qnaDelete(map);
	 * 
	 * map.put("table", "qna"); return dao.qnaDelete(map); }); return affected; }
	 */

	// 댓글 목록
	public List<AnswerDTO> answerList(String qno) {
		return dao.answerList(qno);
	}

	// 댓글 수 카운트
	@Override
	public int answerSearchTotalCount(Map map) {
		return dao.answerSearchTotalCount(map);
	}

	// 댓글 등록
	@Override
	public int answerInsert(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx -> {
			int affectedAnswerInsert = dao.answerInsert(map);
			return affectedAnswerInsert;
		});
		return affected;
	}

	// 댓글 수정
	@Override
	public int answerUpdate(Map map) {
		return dao.answerUpdate(map);
	}

	// 댓글 삭제
	@Override
	public int answerDelete(Map map) {
		int affected = 0;
		affected = transactionTemplate.execute(tx -> {
			int affectedAnswerDelete = dao.answerDelete(map);
			return affectedAnswerDelete;
		});
		return affected;
	}

	// 댓글 전부 삭제
	@Override
	public int answerAllDelete(Map map) {
		int affected = 0;
		List<String> anolists = (List<String>) map.get("ano");
		System.out.println(anolists);
		for (String ano : anolists) {
			Map anoMap = new HashMap();
			anoMap.put("ano", ano);
			System.out.println(anoMap);
			affected += dao.answerAllDelete(anoMap);
		}
		if (affected == ((List) map.get("ano")).size()) {
			return 1;
		} else
			return 0;
	}

	// 이름 찾기
	@Override
	public String findNameByKey(Map map) {
		return dao.findNameByKey(map);
	}

}