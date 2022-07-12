package com.aamu.aamurest.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.aamu.aamurest.user.service.CommuDTO;
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
	
	//글 목록용
	@GetMapping("/bbs/SelectList")
	public List<BBSDTO> bbsSelectList(){
		List<BBSDTO> list = bbsService.bbsSelectList();
		for(BBSDTO dto : list) {
			dto.setPhoto(bbsService.bbsSelectPhotoList(dto.getRbn()));
		}
		return list;
	}
	
	//글 등록용
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
	
	//글 수정용
	@PutMapping("/bbs/edit/{themeid}")
    public Map bbsUpdate(@PathVariable String id,@RequestParam Map map) {
		int bbsUpdateAffected=bbsService.bbsUpdate(map);
		Map resultMap = new HashMap();
		if(bbsUpdateAffected==1) 
			resultMap.put("result", "updateSuccess");
		else 
			resultMap.put("result", "updateNotSuccess");
		return resultMap;
	}
}
	
