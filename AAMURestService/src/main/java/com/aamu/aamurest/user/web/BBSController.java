package com.aamu.aamurest.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aamu.aamurest.user.service.BBSDTO;
import com.aamu.aamurest.user.service.BBSService;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.util.FileUploadUtil;

@CrossOrigin(origins="")
@RestController
@RequestMapping("")
public class BBSController {
	
	@Autowired
	private BBSService bbsservice;
	
	/* 작업중
	@RequestMapping("")
	public List<BBSDTO> BBSSelectList(){
		List<BBSDTO> list = BBSService.BBSSelectList();
		for(BBSDTO dto : list) {
			dto.setBBS(BBSService.BBSSelectOne(dto.getRbn()));
			dto.setPhoto(BBSService.BBSSelectOne(dto.getRbn()));
		}
		return list;
	}
	*/
	
    @PostMapping("/getParam")
    public void getParam(@RequestBody Map<String,String> param) {
 
 		String age = param.get("age");
 		String name = param.get("name"); 
    }
    
	@PostMapping(value="/bbs/edit", produces = "application/json;charset=UTF-8")
	public Map commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		String path=req.getSession().getServletContext().getRealPath("/resources/upload");
		
		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			System.out.println("uploads:"+uploads);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=BBSService.bbsInsert(map);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}
	
	
	
}