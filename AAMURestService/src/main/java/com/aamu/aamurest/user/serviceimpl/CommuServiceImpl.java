package com.aamu.aamurest.user.serviceimpl;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.util.UserUtil;


@Service("commuService")
public class CommuServiceImpl implements CommuService<CommuDTO>{
	@Autowired
	private CommuDAO dao;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private MainDAO mainDao;

	//글 목록용
	@Override
	public List<CommuDTO> commuSelectList(Map map) {
		//페이징
		if(map.get("page") != null) {
			//전체 레코드수
			int totalCount=dao.commuGetTotlaCount(map); //10
			//map.put("totalCount", totalCount);
			//전체 페이지수 
			int totalPage = (int)Math.ceil((double)totalCount/5);//4
			//현재 페이지 번호
			int nowPage=Integer.parseInt(map.get("page").toString().trim()); //2
			//시작 및 끝 ROWNUM구하기
			int start=(nowPage-1)*5+1; //4
			int end=nowPage*5;	//6
			map.put("start", start);
			map.put("end", end);
		}
		
		if(map.get("searchWord") != null) {
			if(map.get("searchWord").toString().contains("#") && map.get("searchWord").toString().length()>=2) {
				String tname=map.get("searchWord").toString().split("#")[1];
				map.put("searchWord", tname);
				//lists=dao.commuSelectList(map);//#서, #서울가 오잖아
			}
		}
		
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
	
	//글 목록용_추천 계정
	@Override
	public List<String> commuRecommendUser(Map map) {//id(세션id)
		//기준되는id (세션id)
		String standId = map.get("id").toString();
		//standTheme 로그인 한 사람 사용자정보 얻기
		List<String> standTheme=infoUser(map);
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		List<String> allUsersNotFollower = new Vector<>();
		//모든 id 얻기 
		List<String> allUsers=mainDao.getAllUser(); 
		//세션id가 팔로우하는 id 얻기
		List<String> idByFollowers=dao.commuGetidByFollower(map); 
		allUsers.removeAll(idByFollowers);
		
		for(String id:allUsers) {
			Map resultMap = new HashMap<>();
			if(!(standId.equals(id))){ //로그인id가 아닐 때 진행
				map.put("id", id); //여기는 로그인id가 아닌 id를 셋팅
				//standTheme 세션id제외 사용자정보 얻기
				List<String> compareTheme = infoUser(map); 
				//교집합/합집합
				double commonins = (double)UserUtil.intersection(standTheme, compareTheme);
				double ins = commonins/
						     (double)(standTheme.size()+compareTheme.size())-commonins;
				resultMap.put("id", id);
				resultMap.put("ins", ins);
				resultList.add(resultMap);
			}
		}
		//내림차순
		Collections.sort(resultList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Double ins1 = (Double) o1.get("ins");
				Double ins2 = (Double) o2.get("ins");
				return ins2.compareTo(ins1);
			}
		}); 
		//모든거 다 가기
		List<String> idList = new Vector<>();
		for(Map<String, Object> result:resultList) {
			idList.add(result.get("id").toString());
		}
		return idList;
	}
	
	//추천_데이타 모으기 메소드 
	public List<String> infoUser(Map map){
		//standTheme 1.아이디가 가진 테마네임리스트 얻기
		List<String> standTheme = mainDao.getUserTheme(map); 
		UsersDTO dto = mainDao.getUserChar(map); //id에 따른 users테이블에 있는 모든것 얻기 
		//standTheme 2.나이대 20대, 30대 ...
		LocalDate now = LocalDate.now();
		int age = now.getYear()-(Integer.parseInt(dto.getSocialnum().substring(0, 2))+1900);
		age = age-age%10; 
		standTheme.add(String.valueOf(age));//standTheme에 나이대 추가
		//standTheme 3.태그(id에 따른)
		List<String> allTags=dao.getAllTags(map);
		for(String allTag:allTags) {
			standTheme.add(allTag);
		}
		//standTheme 4.성별 추가
		standTheme.add(dto.getGender());
		return standTheme;
	}
	
	//글 검색용
	@Override
	public List<String> commuSearachList(Map map) {
		if(map.get("searchColumn").equals("id")) {
			map.put("table", "users");
			List<String> list=dao.commuSearachList(map);
			return list;
		}
		else if(map.get("searchColumn").equals("ctitle")){
			map.put("table", "community");
			return dao.commuSearachList(map);
		}
		else {//tname이 넘어온거 searchColumn:tname , searchWord:서울
			if(map.get("searchWord").toString().contains("#") && map.get("searchWord").toString().length()>=2) { //#서울
				String tname=map.get("searchWord").toString().split("#")[1];
				map.put("tname", tname);
				List<String> tagLists=dao.commuSelectTag(map);//#서, #서울가 오잖아
				List<String> sharptTagList = new Vector<>();
				
				for(String tag:tagLists) { //서울, 서울여행을 꺼내서 
					String sharpTag="#"+tag; //#서울을 붙이기
					sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
				}
				return sharptTagList;		
			}
			else 
				return null;
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
		//int tagAffected=0;
		map.get("tname"); //
		if(map.get("tname")!=null) {
			///////////////////////////
			//tagAffected=insertCommuTagTb(map); //메소드호출 //commutag테이블에 insert
			insertCommuTagTb(map);
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
	
	//글 생성용_태그 뿌려주기
	//TAGS테이블에 있으면 TNO,TNAME 키값으로 뿌려주기 //없으면 INSERT TAGS테이블 COMMUTAG테이블
	@Override
	public List<String> commuTag(Map map) {
		if(map.get("tname").toString().contains("#") && map.get("tname").toString().length()>=2) {
			String tname=map.get("tname").toString().split("#")[1];
			map.put("tname", tname);
			List<String> tagLists=dao.commuSelectTag(map);//#서, #서울가 오잖아
			List<String> sharptTagList = new Vector<>();
			
			for(String tag:tagLists) { //서울, 서울여행을 꺼내서 
				String sharpTag="#"+tag; //#서울을 붙이기
				sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
			}
			return sharptTagList;		
		}
		else 
			return null;
	}

	//글 하나 뿌려주는 용
	@Override
	public CommuDTO commuSelectOne(Map map) {
		CommuDTO dto=dao.commuSelectOne(map);
		System.out.println("셀렉트원 map:"+map);
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
				if(CountTag==0) {//CountTag카운트가 0이면(CountTag테이블에 태그가 하나도 없다) insert만
					insertCommuTagTb(map);
				}
				else {//CountTag카운트가 0이 아니다(CountTag테이블에 태그가 한개이상있다) delete 후 insert
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
	
	//댓글 알림
	@Override
	public String commuSelectUserId(Map map) {
		return dao.commuSelectUserId(map);
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
				resulMap.put("isFollower", true);
				return resulMap;
			}
			else {
				return null;
			}
		}
		else { //follower테이블에 있다 따라서 삭제
			int deleteAffected=dao.commuDeleteFollower(map);
			if(deleteAffected==1) {
				resulMap.put("isFollower", false);
				return resulMap;
			}
			else {
				return null;
			}
		}
	}
	
	//마이페이지용_id에 따른 
	@Override
	public List<CommuDTO> commuMyPageList(Map map) {
		List<CommuDTO> lists=dao.commuMyPageList(map);
		for(CommuDTO list:lists) {
			int affected=dao.commuIsExistFollower(map); //세션아이디가 글쓴이아이디 1이면 
			if(affected == 1) list.setIsFollower(true); 
			else list.setIsFollower(false); 
		}
		return lists;
	}
	
	//마이페이지용_토탈카운트 셋팅 (해당 id의 총 글 갯수)
	@Override
	public int commuTotalCount(Map map) {
		map.put("table", "community");
		return dao.commuTotalCount(map);
	}
	
	//마이페이지용_나를 팔로우하는 계정 수 셋팅(followercount)
	@Override
	public int commuFollowerCount(Map map) {
		map.put("table", "follower");
		return dao.commuTotalCount(map);
	}
	
	//마이페이지용_내가 팔로잉하는 계정 수 셋팅 (followingcount)
	@Override
	public int commuFollowingCount(Map map) {
		return dao.commuFollowingCount(map);
	}
	
	//마이페이지용-팔로우,팔로잉 목록
	@Override
	public List<String> commuMyPageFollower(Map map) {
		return dao.commuMyPageFollower(map);
	}
	
	//워드클라우드_태그네임 얻기
	@Override
	public List<Map> commugetTnames() {
		List<String> tnames=dao.commugetTnames();
		List<Map> tnameMap = new Vector<>();
		
		for(String tname:tnames) {
			Map map = new HashMap<>();
			map.put("text", tname);
			int count=dao.commuGetTnameOfCount(tname);
			map.put("value", count);
			tnameMap.add(map);
		}
		
		return tnameMap;
	}
	
	
	

	////////////////////////////////////////////////////////공통 메소드
	
	public int insertCommuTagTb(Map map) {
		int affected=0;
		List<String> tnameList = new Vector<>();
		for(String tnameString:(List<String>)map.get("tname")) {
			String tnameStr = tnameString.replaceAll(" ", "").substring(1); //서울
			tnameList.add(tnameStr);//[서울] [서울,서울여행]
		}
		for(int i=0; i<tnameList.size();i++) {
			map.put("tname", tnameList.get(i));//tname:서울
			List<String> tagLists=dao.commuSelectTag(map);
			if(tagLists.contains(tnameList.get(i))) {//tags테이블에 있으면
				map.put("tname", tnameList.get(i));
				int tno=dao.selectTnoOfTags(map); //tags테이블에 tno 셀렉트
				map.put("tno", tno);
				affected=dao.commuInsertCommuTag(map); //1. commutag테이블에 저장
			}
			else {
				dao.commuInsertTags(map); //1. tags테이블에 인서트
				map.put("tname", tnameList.get(i));
				int tno=dao.selectTnoOfTags(map); //tags테이블에 tno 셀렉트
				map.put("tno", tno);
				affected=dao.commuInsertCommuTag(map); //2.commutag테이블에 인서트
			}
		}
		return affected;
	}
	
	/////////////////////////////////////////////////////////워드클라우드(관리자)
	@Override
	public List<String> getAllTnamesOfWoman(Map map) {
		return dao.getAllTnamesOfWoman(map);
	}
	
	
	
	
	
	

}
