package com.aamu.admin.main.serviceimpl;

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

	// LineCommentDAO주입
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

	@Override
	public int qnaWrite(Map map) {
		// 영향받은 행의 수가 아니라 새로 입력된 레코드의 키값(lno)를 반환 받는다
		// 마이바티스의 insert()는 무조건 영향받은 행의 수를 반환
		int newAno = dao.insert(map);
		System.out.println("새로 입력된 코멘트의 글번호:" + newAno);
		
		return newAno;
	}

	@Override
	public int qnaDelete(Map map) {
		return dao.delete(map);
	}

	@Override
	public int qnaEdit(Map map) {
		return dao.update(map);
	}

	// map에 lno키 넣기
	@Override
	public String findNameByKey(Map map) {
		return dao.findNameByKey(map);
	}

	@Override
	public int qnaGetTotalRecordCount(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int qnaCount(Map map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}