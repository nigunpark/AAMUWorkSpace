package com.aamu.aamurest.user.serviceimpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.CommuService;

@Service("commuService")
public class CommuServiceImpl implements CommuService<CommuDTO>{
	@Autowired
	private CommuDAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	//글 목록용
	@Override
	public List<CommuDTO> commuSelectList(Map map) {
		List<CommuDTO> lists=dao.commuSelectList(map);
		//isLike셋팅
		List<CommuDTO> returnLists = new Vector();
		for(int i=0; i<lists.size(); i++) {
			CommuDTO dto=lists.get(i);
			map.put("lno", dto.getLno());
			if(dao.commuLikeSelect(map)==1) dto.setIslike(true);
			else dto.setIslike(false);
			returnLists.add(dto);
		}
		return returnLists;
	}

	//글 목록용_댓글 하나 뿌려주기
	@Override
	public CommuCommentDTO commuCommentSelectOne(String lno) {
		return dao.commuCommentSelectOne(lno);
	}

	//글 목록용_사진 뿌려주기
	@Override
	public List commuSelectPhotoList(String lno) {
		return dao.commuSelectPhotoList(lno);
	}
	
	//글 목록용_토탈 카운트 
	@Override
	public int commuSearchTotalCount(Map map) {
		return dao.commuSearchTotalCount(map);
	}

	//글 목록용_좋아요 여부 뿌려주기
	/*
	@Override
	public Boolean commuIsLike(Map map) {
		int isLikeaffected=dao.commuLikeSelect(map);
		if(isLikeaffected == 1) return true;
		else return false;
	}*/
	
	//글 목록용_CommuTag에 레코드값
	@Override
	public int selectCountCommuTag(String lno) {
		return dao.selectCountCommuTag(lno);
	}
	
	//글 목록용_tname 얻기
	@Override
	public List<String> commuSelectTagName(String lno) {
		return dao.commuSelectTagName(lno);
	}
	
	//글 검색용
	@Override
	public List<String> commuSearachList(Map map) {
		if(map.get("searchColumn").equals("id")) {
			//map.put("searchWord", map.get("searchWord").toString()); 어퍼케이스 하려고 넣었나?
			map.put("table", "users");
			List<String> list=dao.commuSearachList(map);
			return list;
		}
		else if(map.get("searchColumn").equals("ctitle")){
			map.put("table", "community");
			return dao.commuSearachList(map);
		}
		else {//tname이 넘어온거 searchColumn:tname , searchWord:서울
			map.put("table", "tags");
			List<String> tnameList=dao.commuSearachList(map);
			List<String> shrapTnameList = new Vector();
			for(String tname:tnameList) {
				String sharpTname="#"+tname;
				shrapTnameList.add(sharpTname);
			}
			return shrapTnameList;
		}
	}

	//글 생성용
	@Override
	public int commuInsert(Map map) {
		//map에 tname있음
		//commu
		int commuaffected=dao.commuInsert(map);
		//사진
		int photoAffected=0;
		List<String> lists= (List<String>) map.get("photolist");
		for(String photo:lists) {
			Map photomap = new HashMap();
			photomap.put("lno", map.get("lno"));
			photomap.put("photo", photo);
			photoAffected+=dao.photoInsert(photomap);
		}
		
		//장소
		if(map.get("contentid")!=null) {
			int placeAffected=dao.placeInsert(map);
			if(placeAffected ==0) return 0;
		}
		
		//태그 저장
		int tagAffected=0;
		if(map.get("tname")!=null) {
			tagAffected=insertCommuTagTb(map); //메소드호출
		}

		if(commuaffected==1 && photoAffected==((List)map.get("photolist")).size())
			return 1;
		else
			return 0;
	}/////commuInsert

	//글 생성용_장소 뿌려주기
	@Override
	public List<Map> commuPlaceList(Map map) {
		return dao.commuPlaceList(map);
	}

	//글 생성용_방금 insert된 글 다시 보내기
	/* 안쓰나?
	@Override
	public CommuDTO commuSelectAfterInsert() {
		return dao.commuSelectAfterInsert();
	}
	*/
	
	//글 생성용_태그 뿌려주기
	//TAGS테이블에 있으면 TNO,TNAME 키값으로 뿌려주기 //없으면 INSERT TAGS테이블 COMMUTAG테이블
	@Override
	public List<String> commuTag(Map map) {
		List<String> tagLists=dao.commuSelectTag(map);
		System.out.println("트루일가요?"+tagLists.contains(map.get("tname")));
		if(tagLists.contains(map.get("tname"))) {
			System.out.println("tname은 뭘가요?"+map.get("tname"));
			List<String> sharptTagList = new Vector<>();
			for(String tag:tagLists) { //서울, 서울여행을 꺼내서 
				String sharpTag="#"+tag; //#서울을 붙이기
				sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
			}
			return sharptTagList;
		}
		else {
			int affected=dao.commuInsertTags(map);
			return tagLists;
		}
	}

	//글 하나 뿌려주는 용
	@Override
	public CommuDTO commuSelectOne(Map map) {
		CommuDTO dto=dao.commuSelectOne(map);
		List<CommuCommentDTO> list=dao.commuCommentList(map.get("lno").toString());
		dto.setCommuCommentList(list);
		//태그 셋팅
		int CountTag=dao.selectCountCommuTag(map.get("lno").toString());
		if(CountTag>0) {
			List<String> tagList=dao.commuSelectTagName(map.get("lno").toString()); //서울,서울여행 이니까 #붙여야됨
			List<String> sharptTagList = new Vector<>();
			for(String tag:tagList) {
				String sharpTag="#"+tag; //#서울을 붙이기
				sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
			}
			dto.setTname(sharptTagList);
		}
		//좋아요 셋팅
		if(dao.commuLikeSelect(map)==1) dto.setIslike(true);
		else dto.setIslike(false);
		return dto;
	}
	
	/*
	 //글 하나 뿌려주는 용
	@Override
	public CommuDTO commuSelectOne(Map map) {
		CommuDTO dto=dao.commuSelectOne(map);
		List<CommuCommentDTO> list=dao.commuCommentList(map.get("lno").toString());
		dto.setCommuCommentList(list);
		//태그 셋팅
		int CountTag=dao.selectCountCommuTag(lno);
		if(CountTag>0) {
			List<String> tagList=dao.commuSelectTagName(lno); //서울,서울여행 이니까 #붙여야됨
			List<String> sharptTagList = new Vector<>();
			for(String tag:tagList) {
				String sharpTag="#"+tag; //#서울을 붙이기
				sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
			}
			dto.setTname(sharptTagList);
		}
	
	 * */

	//글 하나 뿌려주는 용_모든 댓글 뿌려주기
	@Override
	public List<CommuCommentDTO> commuCommentList(String lno) {
		return dao.commuCommentList(lno);
	}

	//글 하나 뿌려주는 용_프로필 뿌려주기
	@Override
	public String commuSelectUserProf(String id) {
		return dao.commuSelectUserProf(id);
	}


	//글 수정용 트랜잭션확인해야됨
	@Override
	public int commuUpdate(Map map) {
		int affected=0;
		affected = transactionTemplate.execute(tx->{
			int affectedCommuUpdate=dao.commuUpdate(map);
			dao.commuPlaceUpdate(map);
			
			//태그 수정
			if(map.get("tname")!=null) { //tname키값으로 tag가 넘어왔을 때
				int CountTag=dao.selectCountCommuTag(map.get("lno").toString());//commutag테이블 count
				if(CountTag==0) {//CountTag카운트가 0이면 insert만
					insertCommuTagTb(map);
				}
				else {//CountTag카운트가 0이 아니다 delete 후 insert
					dao.commuDeleteCommuTag(map);
					insertCommuTagTb(map);
				}
			}/////if
			else {
				dao.commuDeleteCommuTag(map);
			}
			return affectedCommuUpdate;
		});
		return affected;
	}

	//글 수정용_commuplace 수정
	/* 위에랑 합침
	@Override
	public int commuPlaceUpdate(Map map) {
		return dao.commuPlaceUpdate(map);
	}*/

	//글 삭제용
	@Override
	public int commuDelete(String lno) {
		int affected=0;
		Map map = new HashMap<>();
		affected = transactionTemplate.execute(tx->{
			map.put("table", "commucomment");
			map.put("lno", lno);
			dao.commuDelete(map);

			map.put("table", "commuphoto");
			dao.commuDelete(map);

			map.put("table", "commuplace");
			dao.commuDelete(map);

			map.put("table", "likeboard");
			dao.commuDelete(map);
			
			map.put("table", "commutag");
			dao.commuDelete(map);

			map.put("table", "community");
			return dao.commuDelete(map);
		});
		return affected;
	}

	//댓글 생성용
	@Override
	public int commuCommentInsert(Map map) {
		int affected=0;
		affected = transactionTemplate.execute(tx->{
			int affectedCommuCommentInsert=dao.commuCommentInsert(map);
			dao.commuRcPlusUpdate(map);
			return affectedCommuCommentInsert;
		});
		
		return affected;
	}

	//댓글 생성용_Rcount컬럼 +1
	/*
	@Override
	public int commuRcPlusUpdate(Map map) {
		return dao.commuRcPlusUpdate(map);
	}

	*/
	

	//댓글 수정용
	@Override
	public int commuCommentUpdate(Map map) {
		return dao.commuCommentUpdate(map);
	}

	//댓글 삭제용
	@Override
	public int commuCommentDelete(Map map) {
		int affected=0;
		affected = transactionTemplate.execute(tx->{
			int affectedCommuCommentDelete =dao.commuCommentDelete(map);
			//댓글 삭제용_Rcount컬럼 -1
			dao.commuRcMinusUpdate(map);
			return affectedCommuCommentDelete;
		});
		return affected;
	}
	
	//글 좋아요 전체리스트
	@Override
	public Boolean commuLike(Map map) {
		//likeboard테이블 select count(*)~where id=좋아요누른id
		int commuLikeSelectAffected=dao.commuLikeSelect(map); //1이면 좋아요누른id가 존재하는거 0이면 처음누른거
		int commuLikeInsertAffected, commuLikeUpdateAffected, commuLikeDeleteAffected=0;
		Map resultMap = new HashMap();
		//글 좋아요_insert(likeboard 테이블),update(community테이블의 likecount+1)
		if(commuLikeSelectAffected==0) {//0이면 해당id로 처음 누른거니까 likeboard-insert community-update 둘다 진행
			//likeboard테이블에 insert
			commuLikeInsertAffected=dao.commuLikeInsert(map);
			//community테이블의 likecount update
			commuLikeUpdateAffected=dao.commuLikePlusUpdate(map);
			if(commuLikeInsertAffected==1 && commuLikeUpdateAffected==1) return true;
			else return null;
		}
		//글 좋아요 취소_delete(likeboard테이블),update(community테이블의 likecount-1)
		else {//이미 likeboard테이블에 있는데 또 눌렀다는거 == 좋아요 취소
			commuLikeDeleteAffected=dao.commuLikeDelete(map);
			commuLikeUpdateAffected=dao.commuLikeMinusUpdate(map);
			if(commuLikeDeleteAffected==1 && commuLikeUpdateAffected==1) return false;
			else return null;
		}
	}

	//글 좋아요_selectOne(community테이블의 likecount)
	@Override
	public int commuLikecountSelect(Map map) {
		return dao.commuLikecountSelect(map);
	}
	
	//팔로우, 팔로잉
	@Override
	public Map commuFollower(Map map) {
		int affected=dao.commuIsExistFollower(map); //follower테이블에 있는가? 있으면1 없으면0
		Map resulMap = new HashMap<>();
		if(affected==0) { //follower테이블에 없다 따라서 넣어줘야됨
			int insertAffected=dao.commuInsertFollower(map);
			if(insertAffected==1) {
				resulMap.put("FollowerSuccess", true);
				return resulMap;
			}
			else {
				resulMap.put("FollowerSuccess", false);
				return resulMap;
			}
		}
		else { //follower테이블에 있다 따라서 삭제
			int deleteAffected=dao.commuDeleteFollower(map);
			if(deleteAffected==1) {
				resulMap.put("FollowerCancelSuccess", true);
				return resulMap;
			}
			else {
				resulMap.put("FollowerCancelSuccess", true);
				return resulMap;
			}
		}
	}
	
	//마이페이지용_id에 따른 
	@Override
	public List<CommuDTO> commuMyPageList(Map map) {
		return dao.commuMyPageList(map);
	}
	
	//마이페이지용_토탈카운트 셋팅 (해당 id의 총 글 갯수)
	@Override
	public int commuTotalCount(Map map) {
		map.put("table", "community");
		return dao.commuTotalCount(map);
	}
	
	//마이페이지용_나를 팔로우하는 계정 수 셋팅
	@Override
	public int commuFollowerCount(Map map) {
		map.put("table", "follower");
		return dao.commuTotalCount(map);
	}
	
	//마이페이지용_내가 팔로잉하는 계정 수 셋팅
	@Override
	public int commuFollowingCount(Map map) {
		return dao.commuFollowingCount(map);
	}
	
	////////////////////////////////////////////////////////공통 메소드
	
	//insertCommuTag 메소드
	public int insertCommuTagTb(Map map) {
		int affected=0;
		String tnameString=map.get("tname").toString();
		String[] tnameArr = tnameString.split("#");
		for(int i=1; i<tnameArr.length;i++) {
			map.put("tname", tnameArr[i]);
			System.out.println("메소드 타니?"+map.get("tname"));
			int tno=dao.selectTnoOfTags(map);
			map.put("tno", tno);
			System.out.println("메소드 타니? tno"+map.get("tno"));
			affected=dao.commuInsertCommuTag(map);
		}
		return affected;
	}
	
	
	

}
