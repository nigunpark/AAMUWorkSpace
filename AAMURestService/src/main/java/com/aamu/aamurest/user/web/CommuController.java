package com.aamu.aamurest.user.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.serviceimpl.CommuServiceImpl;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin(origins="*")
public class CommuController {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CommuServiceImpl commuService;

	@Autowired
	private CommonsMultipartResolver multipartResolver;



	//글 목록용
	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(@RequestParam Map map, HttpServletRequest req){
		//검색할 때는 맵으로 써치워드 써치컬럼을 받고, id는 isLike때문에 받는거다. lno는 dto에서 뽑아온다
		//cid가 넘어오면 마이페이지 id에 따른 글 뿌려주기
		System.out.println("셀렉트 리스트 id:"+map.get("id"));
		System.out.println("셀렉트 리스트 searchColumn:"+map.get("searchColumn"));
		System.out.println("셀렉트 리스트 searchWord:"+map.get("searchWord"));
		//List<CommuDTO> list();
		//list=글 목록들
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트한개 셋팅 
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//코멘트의 프로필 셋팅
			CommuCommentDTO commentdto=dto.getCommuComment();
			//전체 코멘트 셋팅
			//모든 댓글 가져오기
			List<CommuCommentDTO> commentList=commuService.commuCommentList(dto.getLno());
			dto.setCommuCommentList(commentList);
			//커뮤태그에 레코드 1이상이면 태그네임 셋팅하기 
			int CountTag=commuService.selectCountCommuTag(dto.getLno());
			if(CountTag>0) {
				List<String> tagList=commuService.commuSelectTagName(dto.getLno()); //서울,서울여행 이니까 #붙여야됨
				List<String> sharptTagList = new Vector<>();
				for(String tag:tagList) {
					String sharpTag="#"+tag; //#서울을 붙이기
					sharptTagList.add(sharpTag); //새로운 배열에 담아서 전달
				}
				dto.setTname(sharptTagList);
			}
			//토탈카운트
			//System.out.println("포함되어있냐"+map.keySet().contains("searchColumn"));
			if(map.keySet().contains("searchColumn")) { 
				dto.setSearchtotalcount(commuService.commuSearchTotalCount(map));
			}
			//코멘트 프로필사진 셋팅
			if(commentdto!=null) {
				commentdto.setUserprofile(FileUploadUtil.requestOneFile(commuService.commuSelectUserProf(commentdto.getId()), "/resources/commuUpload", req));
			}
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
			//글쓴이-프로필 사진 가져와서 dto에 셋팅
			dto.setUserprofile(FileUploadUtil.requestOneFile(commuService.commuSelectUserProf(dto.getId()), "/resources/commuUpload", req));
			
		}/////for
		System.out.println("몇개 넘어가니:"+list.size());
		return list;
	}////////////////commuSelectList
	
	//글 검색용_id로 검색
	/*
	@GetMapping("/gram/place/selectList")
	public List<Map> commuPlaceList(@RequestParam Map map) {
		List<Map> list=commuService.commuPlaceList(map);
		return list;
	}
	*/
	//글 검색용_searchColumn:id,ctitle,tname로 검색
	@GetMapping("/gram/search/selectList")
	public List<String> commuSearachList(@RequestParam Map map){
		System.out.println("검색 searchColumn:"+map.get("searchColumn"));
		System.out.println("검색 searchWord:"+map.get("searchWord"));
		List<String> list=commuService.commuSearachList(map);
		return list;
	}

	//글 생성용: 리스트 ver
	/*
	@PostMapping(value="/gram/edit")
	public List<CommuDTO> commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		//서버의 물리적 경로 얻기
		String path=req.getSession().getServletContext().getRealPath("/resources/commuUpload");

		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}

		int affected=commuService.commuInsert(map);
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트 셋팅
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		}/////for
		return list;
	}////////////////////////////
	*/

	//글 생성용: true false
	@PostMapping(value="/gram/edit")
	public Map commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		System.out.println("tname이 오나?"+map.get("tname"));
		//서버의 물리적 경로 얻기
		String path=req.getSession().getServletContext().getRealPath("/resources/commuUpload");
		Map resultMap = new HashMap();

		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}

		int affected=commuService.commuInsert(map);
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트 셋팅
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		}/////for
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;

	}////////////////////////////

	//글 생성용_장소 뿌려주기
	@GetMapping("/gram/place/selectList")
	public List<Map> commuPlaceList(@RequestParam Map map) {
		List<Map> list=commuService.commuPlaceList(map);
		return list;
	}
	
	//글 생성용_태그 뿌려주기!!!!!!!!!!!!!!!!!!!!!
	//tname이라는 키값으로 #여행이 넘어와 
	//TAGS테이블에 있으면 TNO,TNAME 키값으로 뿌려주고 COMMUTAG에 저장 //없으면 INSERT TAGS테이블 COMMUTAG테이블
	@GetMapping("/gram/tag")
	public List<String> commuTag(@RequestParam Map map){
		return commuService.commuTag(map);
	}

	//글 하나 뿌려주는 용
	@GetMapping("/gram/SelectOne/{lno}")
	public CommuDTO commuSelectOne(@PathVariable String lno, HttpServletRequest req) {
		System.out.println("셀렉트원lno:"+lno);
		CommuDTO dto=commuService.commuSelectOne(lno);
		//모든 사진 가져와서 dto에 셋팅
		dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		//글쓴이-프로필 사진 가져와서 dto에 셋팅
		dto.setUserprofile(FileUploadUtil.requestOneFile(commuService.commuSelectUserProf(dto.getId()), "/resources/commuUpload", req));
		//모든 댓글 가져오기
		List<CommuCommentDTO> commentList=commuService.commuCommentList(lno);
		//댓글-프로필 사진 가져와서 dto에 셋팅
		for(CommuCommentDTO commentDto:commentList) {
			//System.out.println(commuService.commuSelectUserProf(commentDto.getId()));
			commentDto.setUserprofile(FileUploadUtil.requestOneFile(commuService.commuSelectUserProf(commentDto.getId()), "/resources/commuUpload", req));
		}
		dto.setCommuCommentList(commentList);
		System.out.println("셀렉트원 디티오 넘어가니:"+dto);
		return dto;
	}

	//글 수정용: List ver
	/*
	@PutMapping("/gram/edit")
	public List<CommuDTO> commuUpdate(@RequestBody Map map, HttpServletRequest req) {
		int affected=commuService.commuUpdate(map);

		//글 목록 뿌려주기
		List<CommuDTO> lists=commuSelectList(map, req);

		if(affected==1) return lists;
		else return null;
	}
	*/

	//글 수정용: Map ver
	@PutMapping("/gram/edit")
	public Map commuUpdate(@RequestBody Map map) { 
		System.out.println("글수정 map:"+map);
		int affected=commuService.commuUpdate(map);
		Map resultMap = new HashMap<> ();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}

	//글 삭제용
	@DeleteMapping("/gram/edit/{lno}")
	public Map commuDelete(@PathVariable String lno, HttpServletRequest req) {
		System.out.println("글삭제 lno:"+lno);
		List<String> photoLists=commuService.commuSelectPhotoList(lno);
		String path=req.getSession().getServletContext().getRealPath("/resources/commuUpload");
		
		try {
			FileUploadUtil.fileDeletes(photoLists, path);
		} catch (IllegalStateException | IOException e) {}
		
		int affected=commuService.commuDelete(lno);
		Map map = new HashMap<> ();
		if(affected == 1) map.put("isSuccess", true);
		else map.put("isSuccess", false);
		return map;
	}
	//댓글 생성용 - List ver
	/*
	@PostMapping("/gram/comment/edit")
	public List<CommuDTO> commuCommentInsert(@RequestBody Map map, HttpServletRequest req) {
		System.out.println("댓글생성 map:"+map);
		int commuCommentAffected=commuService.commuCommentInsert(map);
		int RcPlusAffected=commuService.commuRcPlusUpdate(map);
		//list=글 목록들
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트 셋팅
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		}/////for
		return list;
	}
	*/
	
	//댓글 생성용 - Map ver
	@PostMapping("/gram/comment/edit")
	public Map commuCommentInsert(@RequestBody Map map) {
		System.out.println("댓글생성 reply:"+map.get("reply"));
		int affected=commuService.commuCommentInsert(map);
		Map resultMap = new HashMap<> ();
		if(affected == 1) {
			resultMap.put("id", map.get("id"));
			resultMap.put("reply", map.get("reply"));
		}
		else resultMap.put("isSuccess", false);
		return resultMap;
	}
	
	//댓글 수정용
	@PutMapping("/gram/comment/edit")
	public Map commuCommentUpdate(@RequestBody Map map) {
		System.out.println("(cc)map:"+map);
		int commuCommentUpdateAffected=commuService.commuCommentUpdate(map);
		Map resultMap = new HashMap();
		if(commuCommentUpdateAffected == 1)
			resultMap.put("result", "UpdateCommentSuccess");
		else
			resultMap.put("result", "UpdateCommentNotSuccess");
		return resultMap;
	}

	//댓글 삭제용
	@DeleteMapping("/gram/comment/edit")
	public Map commuCommentDelete(@RequestParam Map map) {
		int affected=commuService.commuCommentDelete(map);
		//rcount -1
		//int RcMinusAffected=commuService.commuRcMinusUpdate(map);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}

	//글 좋아요 누르기 전체리스트
	/*
	@GetMapping("/gram/like")
	public List<CommuDTO> commuLike(@RequestParam Map map, HttpServletRequest req) {
		System.out.println("라이크 map:"+map);
		int affected=commuService.commuLike(map);
		//community테이블의 selectone likecount
		int likecount=commuService.commuLikecountSelect(map);
		Map resultMap = new HashMap();
		if(affected==1) {
			//list=글 목록들
			List<CommuDTO> list = commuService.commuSelectList(map);
			for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
				//코멘트 셋팅
				dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
				//포토 셋팅
				dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
			}/////for
			return list;
		}
		else return null;
	}
	*/
	//글 좋아요 누르기
	@GetMapping("/gram/like")
	public Map commuLike(@RequestParam Map map) {
		Boolean affected=commuService.commuLike(map);
		//community테이블의 selectone likecount
		int likecount=commuService.commuLikecountSelect(map);
		Map resultMap = new HashMap();
		if(affected) { //true면
			resultMap.put("isLike", true);
			resultMap.put("likecount", likecount);
			resultMap.put("lno", map.get("lno"));
		}
		else {
			resultMap.put("isLike", false);
			resultMap.put("likecount", likecount);
			resultMap.put("lno", map.get("lno"));
		}
		return resultMap;
	}
	
	//팔로우, 팔로잉 
	@PostMapping("/gram/follower")
	public Map commuFollower(@RequestBody Map map) {
		//map에 id:로그인한사람이 누른 id follower:내 id
		int affected=commuService.commuFollower(map);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("isSuccess", true);
		else resultMap.put("isSuccess", false);
		return resultMap;
	}
	
	//마이페이지용_id에 따른 
	


}
