package com.aamu.aamurest.user.serviceimpl.admin;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.admin.NoticeDTO;
import com.aamu.aamurest.user.service.admin.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService<NoticeDTO> {

	@Autowired
	private NoticeDAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	// 게시물 리스트
	@Override
	public List<NoticeDTO> noticeSelectList(Map map) {
		List<NoticeDTO> lists = dao.noticeSelectList(map);

		System.out.println("이런: " + lists);

		// isLike셋팅
		List<NoticeDTO> returnLists = new Vector();
		for (int i = 0; i < lists.size(); i++) {
			NoticeDTO dto = lists.get(i);
			map.put("nno", dto.getNno());
			returnLists.add(dto);
		}

		System.out.println("아악: " + returnLists);

		return returnLists;
	}

	// 게시물 수 카운트
	@Override
	public int noticeSearchTotalCount(Map map) {
		return dao.noticeSearchTotalCount(map);
	}

	// 게시물 검색
	@Override
	public List<String> noticeSearachList(Map map) {
		if (map.get("searchColumn").equals("id")) {
			// map.put("searchWord", map.get("searchWord").toString()); 어퍼케이스 하려고 넣었나?
			map.put("table", "users");
			List<String> list = dao.noticeSearachList(map);
			return list;
		} else if (map.get("searchColumn").equals("title")) {
			map.put("table", "notice");
			return dao.noticeSearachList(map);
		} else {// tname이 넘어온거 searchColumn:tname , searchWord:서울
			map.put("table", "tags");
			List<String> nameList = dao.noticeSearachList(map);
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
	public NoticeDTO noticeSelectOne(Map map) {
		NoticeDTO dto = dao.noticeSelectOne(map);
		return dto;
	}

	// 게시물 등록
	@Override
	public int noticeInsert(Map map) {

		int noticeaffected = dao.noticeInsert(map);

		if (noticeaffected == 1)
			return 1;
		else
			return 0;
	}

	// 게시물 수정
	@Override
	public int noticeUpdate(Map map) {
		return dao.noticeUpdate(map);
	}

	// 게시물 삭제
	@Override
	public int noticeDelete(Map map) {
		return dao.noticeDelete(map);
	}

}