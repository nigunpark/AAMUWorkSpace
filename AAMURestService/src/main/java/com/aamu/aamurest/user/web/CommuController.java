package com.aamu.aamurest.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
		//list=글 목록들
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트 셋팅
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		}/////for
		
		return list;
	}////////////////commuSelectList
	
	
	//글 생성용
	@PostMapping(value="/gram/edit")
	public List<CommuDTO> commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		//서버의 물리적 경로 얻기
		String path=req.getSession().getServletContext().getRealPath("/resources/commuUpload");
		
		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=commuService.commuInsert(map);
		//방금 insert된 게시물 dto로 보내기
		//CommuDTO dto = commuService.commuSelectAfterInsert();
		//포토 셋팅
		//dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		List<CommuDTO> list = commuService.commuSelectList(map);
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
			//코멘트 셋팅
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			//포토 셋팅
			dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		}/////for
		return list;
		//Map resultMap = new HashMap();
		//if(affected==1) resultMap.put("result", "insertSuccess");
		//else resultMap.put("result", "insertNotSuccess");
		//return resultMap;
	}////////////////////////////commuInsert
	
	//글 생성용_장소 뿌려주기
	@GetMapping("/gram/place/selectList")
	public List<Map> commuPlaceList(@RequestParam Map map) {
		List<Map> list=commuService.commuPlaceList(map);
		return list;
	}
	
	//글 하나 뿌려주는 용
	@GetMapping("/gram/SelectOne/{lno}")
	public CommuDTO commuSelectOne(@PathVariable String lno, HttpServletRequest req) {
		System.out.println("셀렉트원lno:"+lno);
		CommuDTO dto=commuService.commuSelectOne(lno);
		//모든 댓글 가져오기
		dto.setCommuCommentList(commuService.commuCommentList(lno));
		//모든 사진 가져오기
		dto.setPhoto(FileUploadUtil.requestFilePath(commuService.commuSelectPhotoList(dto.getLno()), "/resources/commuUpload", req));
		return dto;
	}
	
	//글 수정용
	@PutMapping("/gram/edit/{id}")
	public Map commuUpdate(@PathVariable String id,@RequestParam Map map) {
		int commuUpdateAffected=commuService.commuUpdate(map);
		//commuplace에 contentid수정
		int commuPlaceUpdateAffected=commuService.commuPlaceUpdate(map);
		Map resultMap = new HashMap();
		if(commuUpdateAffected==1 && commuPlaceUpdateAffected==1) 
			resultMap.put("result", "updateSuccess");
		else 
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}
	
	//글 삭제용
	@DeleteMapping("/gram/edit/{lno}")
	public Map commuDelete(@PathVariable String lno) {
		int commuDeleteAffected=commuService.commuDelete(lno);
		Map map = new HashMap();
		if(commuDeleteAffected == 1)
			map.put("result", "deleteSuccess");
		else
			map.put("result", "deleteNotSuccess");
		return map;
	}
	
	//댓글 생성용
	@PostMapping("/gram/comment/edit")
	public CommuCommentDTO commuCommentInsert(@RequestParam Map map) {
		int commuCommentAffected=commuService.commuCommentInsert(map);
		int RcPlusAffected=commuService.commuRcPlusUpdate(map);
		//방금 입력한 댓글 뿌려주기
		CommuCommentDTO dto=commuService.commuCommentSelectOne((String)map.get("lno"));
		if(commuCommentAffected == 1 && RcPlusAffected ==1)
			return dto;
		else
			return null;
	}
	
	//댓글 수정용
	@PutMapping("/gram/comment/edit")
	public Map commuCommentUpdate(@RequestParam Map map) {
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
		int commuCommentDeleteAffected=commuService.commuCommentDelete(map);
		//rcount -1
		int RcMinusAffected=commuService.commuRcMinusUpdate(map);
		Map resultMap = new HashMap();
		if(commuCommentDeleteAffected == 1 && RcMinusAffected==1) resultMap.put("result", "DeleteCommentSuccess");
		else resultMap.put("result", "DeleteCommentNotSuccess");
		return resultMap;
	}
	
	//글 좋아요
	@GetMapping("/gram/like")
	public List<CommuDTO> commuLike(@RequestParam Map map, HttpServletRequest req) {
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
	

}
