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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.serviceimpl.BBSServiceImpl;
import com.aamu.aamurest.util.FileUploadUtil;

@CrossOrigin(origins="*")
@RestController
public class BBSController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BBSServiceImpl bbsService;
	
	@Autowired
	private CommonsMultipartResolver multipartResolver;
	
	//글 목록
	@GetMapping("/bbs/SelectList")
	public List<BBSDTO> bbsSelectList(HttpServletRequest req){
		List<BBSDTO> list = bbsService.bbsSelectList();
		System.out.println("list:"+list);
		for(BBSDTO dto:list) {
			//모든 사진 가져오기
			dto.setPhoto(FileUploadUtil.requestFilePath(bbsService.bbsSelectPhotoList(dto.getRbn()), "/resources/bbsUpload", req));
			//dto.setPhoto(dao.bbsSelectPhotoList(rbn));
			//모든 리뷰 가져오기
			dto.setReviewList(bbsService.reviewList(dto.getRbn()));
			
		}
		return list;
	}
	
	//글 등록 <성공>
	@PostMapping("/bbs/edit")
	public Map bbsInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		//서버의 물리적 경로 얻기
		String path=req.getSession().getServletContext().getRealPath("/resources/bbsUpload");
				
		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			System.out.println("(bbsController)uploads:"+uploads);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=bbsService.bbsInsert(map);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "insertSuccess");
		else resultMap.put("result", "insertNotSuccess");
		return resultMap;
	}
	
	//글 하나 선택
	@GetMapping("/bbs/SelectOne/{rbn}")
	public BBSDTO bbsSelectOne(@PathVariable int rbn, HttpServletRequest req) {
		BBSDTO dto=bbsService.bbsSelectOne(rbn);
		//모든 리뷰 가져오기
		dto.setReviewList(bbsService.reviewList(rbn));
		//모든 사진 가져오기
		//dto.setPhoto(bbsService.bbsSelectPhotoList(rbn));
		dto.setPhoto(FileUploadUtil.requestFilePath(bbsService.bbsSelectPhotoList(dto.getRbn()), "/resources/bbsUpload", req));
		return dto;
	}
	
	//글 수정 <성공>
	@PutMapping("/bbs/edit")
    public Map bbsUpdate(@RequestParam Map map) {
		int bbsUpdateAffected=bbsService.bbsUpdate(map);
		Map resultMap = new HashMap();
		if(bbsUpdateAffected==1) 
			resultMap.put("result", "updateSuccess");
		else
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}
	
	//글 삭제 <성공>
	@DeleteMapping("/bbs/edit")
	public Map bbsDelete(@RequestParam Map map) {
		int bbsDeleteAffected=bbsService.bbsDelete(map);
		Map resultMap = new HashMap();
		if(bbsDeleteAffected==1)
			resultMap.put("result", "deleteSuccess");
		else
			resultMap.put("result", "deleteNotSuccess");
		return resultMap;
		
	}
	/*
	//리뷰 목록
	@GetMapping("/review/SelectList")
	public List<ReviewDTO> reviewSelectList(@RequestParam Map map){
		List<ReviewDTO> list = bbsService.reviewList(map);
		return list;
	} */

	//리뷰 등록 <성공>
	@PostMapping("/review/edit")
	public Map reviewInsert(@RequestParam Map map) {
	int affected=bbsService.reviewInsert(map);
	Map resultMap = new HashMap();
	if(affected==1) resultMap.put("result", "insertSuccess");
	else resultMap.put("result", "insertNotSuccess");
	return resultMap;
	}
	
	//리뷰 수정 <성공>
	@PutMapping("/review/edit")
    public Map reviewUpdate(@RequestParam Map map) {
		int reviewUpdateAffected=bbsService.reviewUpdate(map);
		Map resultMap = new HashMap();
		if(reviewUpdateAffected==1)
			resultMap.put("result", "updateSuccess");
		else
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}
	
	//리뷰 삭제 <성공>
	@DeleteMapping("/review/edit")
	public Map reviewDelete(@RequestParam Map map) {
		int reviewDeleteAffected=bbsService.reviewDelete(map);
		Map resultMap = new HashMap();
		if(reviewDeleteAffected==1) 
			resultMap.put("result", "deleteSuccess");
		else
			resultMap.put("result", "deleteNotSuccess");
		return resultMap;
		
	}

	/*
	//평점 반영
	
	//평점 평균 반영
	public double getRatingAverage(int rno) {
	/*
	Double ratingAvg = BBSServiceImpl.getRatingAverage(rno);	
	if(ratingAvg == null) {
		ratingAvg = 0.0;
	}
	
	ratingAvg = (double) (Math.round(ratingAvg*10));
	ratingAvg = ratingAvg / 10;
	
	ReviewDTO rd = new ReviewDTO();
	rd.setId(rd);
	rd.setRatingAvg(ratingAvg);	
	
	replyMapper.updateRating(urd);	
	}*/
	//평점 평균 

}
	
