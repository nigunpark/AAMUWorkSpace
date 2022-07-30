package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aamu.admin.main.service.AnswerDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.QNAService;

@Service("answerService")
public class AnswerServiceImpl implements QNAService<AnswerDTO> {

	@Autowired
	private AnswerDAO dao;

	@Override
	public ListPagingData<AnswerDTO> selectList(Map map, HttpServletRequest req, int nowPage) {
		List answer = dao.selectList(map);
		return ListPagingData.builder().lists(answer).build();
	}

	@Override
	public AnswerDTO selectOne(Map map) {
		return null;
	}

	// 댓글 등록
	@Override
	public int qnaWrite(Map map) {
		int newAno = dao.insert(map);
		System.out.println("새로 입력된 코멘트의 글번호:" + newAno);

		return newAno;
	}

	// 댓글 수정
	@Override
	public int answerUpdate(Map map) {
		return dao.update(map);
	}

	// 댓글 삭제
	@Override
	public int answerDelete(Map map) {
		return dao.delete(map);
	}

	// 댓글 전체 삭제
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

	// 게시물 수 카운트
	@Override
	public int qnaGetTotalRecordCount(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 조회 수 카운트
	@Override
	public int qnaCount(Map map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	// 댓글 수정
	@Override
	public int qnaEdit(Map map) {
		return dao.update(map);
	}

	// 게시물 목록에서 삭제
	@Override
	public int qnaDelete(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 상세 보기에서 삭제
	@Override
	public int qnaViewDelete(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 댓글 등록
	@Override
	public int answerWrite(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

}