package com.aamu.admin.main.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.admin.main.service.AdminCommuCommentDTO;
import com.aamu.admin.main.service.AdminCommuDTO;
import com.aamu.admin.main.service.AdminCommuService;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.service.PagingUtil;

@Service
public class AdminCommuServiceImpl implements AdminCommuService {
	
	@Autowired
	private AdminCommuDAO dao;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	//리소스파일(paging.properties)에서 읽어오기
	@Value("${pageSize}")
	private int pageSize;
	@Value("${blockPage}")
	private int blockPage;
	
	//전체 글 뿌려주기
	@Override
	public ListPagingData<AdminCommuDTO> commuSelectList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.commuGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.commuSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Commu.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<AdminCommuDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 게시물 수 뿌려주기
	@Override
	public int commuGetTotalRecordCount(Map map) {
		return dao.commuGetTotalRecordCount(map);
	}
	
	//글 삭제
	@Override
	public int commuDelete(Map map) {
		int affected=0;
		affected = transactionTemplate.execute(tx->{
			int resultAffectd=0;
			List<String> lnoLists=(List<String>)map.get("lno");
			
			for(String lno:lnoLists) {
				map.put("table", "commucomment");
				map.put("lno", lno); 
				dao.commuDelete(map);
				
				map.put("table", "commuphoto");
				System.out.println("여기 왜 안타");
				dao.commuDelete(map);
				
				map.put("table", "commuplace");
				dao.commuDelete(map);
				
				map.put("table", "likeboard");
				dao.commuDelete(map);
				
				map.put("table", "commutag");
				dao.commuDelete(map);
				
				map.put("table", "community");
				resultAffectd=dao.commuDelete(map);
			}
			return resultAffectd;
		});
		
		return affected;
	}
	
	//댓글 뿌려주기
	@Override
	public ListPagingData<AdminCommuCommentDTO> commuCommentList(Map map, HttpServletRequest req, int nowPage) {
		//페이징을 위한 로직 시작]
		//전체 레코드수
		int totalCount=dao.commuCommentGetTotalRecordCount(map);
		map.put(PagingUtil.PAGE_SIZE, pageSize);
		map.put(PagingUtil.BLOCK_PAGE, blockPage);
		map.put(PagingUtil.TOTAL_COUNT, totalCount);
		//페이징과 관련된 값들 얻기를 위한 메소드 호출
		PagingUtil.setMapForPaging(map);
		//글 전체 목록 얻기
		List lists= dao.commuCommentSelectList(map);
		
		String pagingString = PagingUtil.pagingBootStrapStyle(
				Integer.parseInt(map.get(PagingUtil.TOTAL_COUNT).toString()), 
				Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()), 
				Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()), 
				Integer.parseInt(map.get(PagingUtil.NOW_PAGE).toString()), 
				req.getContextPath()+"/Commu.do?");
		
		//Lombok라이브러리 사용시
		ListPagingData<AdminCommuCommentDTO> listPagingData =ListPagingData.builder().lists(lists).map(map).pagingString(pagingString).build();
		
		return listPagingData;
	}
	
	//전체 댓글 수 뿌려주기
	@Override
	public int commuCommentGetTotalRecordCount(Map map) {
		return dao.commuCommentGetTotalRecordCount(map);
	}
	
	//댓글 삭제
	@Override
	public int commuCommentDelete(Map map) {
		int affected=0;
		
		List<String> cnolists=(List<String>)map.get("cno");
		for(String cno:cnolists) {
			Map cnoMap = new HashMap(); 
			cnoMap.put("cno",cno); 
			System.out.println(cnoMap);
			affected+=dao.commuCommentDelete(cnoMap);
		}
		if(affected==((List)map.get("cno")).size()) {
			return 1;
		}
		else
			return 0;
	}
	
	//사진 목록 가져오기 
	@Override
	public List<String> commuSelectPhotoList(Map map) {
		return dao.commuSelectPhotoList(map);
	}
	
	/////////////////////////////////////////////////////////////////////
		
	//커뮤니티 통계
	@Override
	public Map commuTotal() {
		Map map = new HashMap<>();
		//월별
		map.put("commuMonthTotal", dao.commuMonthTotal(map));
		
		//성별 레코드 카운트
		map.put("gender", "여자");
		int femaleRecordCount=dao.genderPercent(map);
		
		map.put("gender", "남자");
		int maleRecordCount=dao.genderPercent(map);
		
		//비율
		map.put("femaleRecordCount", femaleRecordCount);
		map.put("maleRecordCount", maleRecordCount);
		
		
		return map;
	}
	
	//커뮤니티 통계_
	@Override
	public List<AdminCommuDTO> bestUsersList() {
		//베스트 글쓴이 
		List<AdminCommuDTO> lists=dao.bestUsersList();
		return lists;
	}
	
	//커뮤니티 통계_프로필 뿌려주기
	@Override
	public String commuSelectUserProf(String id) {
		return dao.commuSelectUserProf(id);
	}
	

}
