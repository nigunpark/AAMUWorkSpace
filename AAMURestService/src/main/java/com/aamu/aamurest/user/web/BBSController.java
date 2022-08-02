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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.ReviewDTO;
import com.aamu.aamurest.user.service.UsersDTO;
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
	
	//글 하나 선택
	@GetMapping("/bbs/SelectOne/{rbn}")
	public BBSDTO bbsSelectOne(@PathVariable int rbn, HttpServletRequest req) {
		System.out.println("선택:"+rbn);
		BBSDTO dto=bbsService.bbsSelectOne(rbn);
		
		System.out.println("머가나오나:"+dto);
		//모든 리뷰 가져오기
		System.out.println("리뷰리스트에 뭐가 있을까?"+bbsService.reviewList(rbn));
		dto.setReviewList(bbsService.reviewList(rbn));
		//모든 사진 가져오기
		//dto.setPhoto(bbsService.bbsSelectPhotoList(rbn));
		dto.setPhoto(FileUploadUtil.requestFilePath(bbsService.bbsSelectPhotoList(dto.getRbn()), "/resources/bbsUpload", req));
		return dto;
	}
	
	//글 등록 <성공>
		@PostMapping("/bbs/edit")
		public Map bbsInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		System.out.println("등록:"+map);
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

	//글 수정 <성공>
	@PutMapping("/bbs/edit")
    public Map bbsUpdate(@RequestBody Map map) {
		System.out.println("글 수정:"+map);
		int bbsUpdateAffected=bbsService.bbsUpdate(map);
		Map resultMap = new HashMap<> ();
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
	//평균 평점 반영
	@PutMapping("/review/edit/{rbn}")
	public Map updateRate(@PathVariable int rbn, HttpServletRequest req, @RequestParam Map map){
		double ReviewUpdateAffected = bbsService.updateRate(rbn);
		Map resultMap = new HashMap();
		if(ReviewUpdateAffected==1)
			resultMap.put("result", "updateSuccess");
		else
			resultMap.put("result", "updateNotSuccess");

		return resultMap;
	}
	*/

	//리뷰 등록 <성공>
	//평균 평점 반영 <성공>
	@PostMapping("/review/edit")
	public Map reviewInsert(@RequestBody Map map) {
		System.out.println("map이 넘어오나:"+map);
		int rbn = Integer.parseInt(map.get("rbn").toString());
		List<ReviewDTO> list = bbsService.reviewList(rbn);
		double total = Double.parseDouble(map.get("rate").toString());
		if(list.size() == 0) {
			for(ReviewDTO dto:list) {
				total += dto.getRate();
			}
			total = Math.round(total / (list.size())*10);
			total = total/10;
		}
		
		map.put("rateavg", total);
		bbsService.updateRate(map);
		int affected=bbsService.reviewInsert(map);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "insertSuccess");
		else resultMap.put("result", "insertNotSuccess");
		return resultMap;
	}
	
	/*
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
	}*/

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
	
	//검색결과 목록
	//@GetMapping("/getSearchList")
	
	//테마 사진 하나 뿌려주기
	@GetMapping("/theme/SelectOne/{themeid}")
	public BBSDTO themeSelectOne(@RequestParam Map map,HttpServletRequest req) {
		BBSDTO dto = bbsService.themeSelectOne(map);
		dto.setThemeimg(FileUploadUtil.requestOneFile(dto.getThemeimg(), "/resources/themeUpload", req));
		return dto;
	}
	
}

