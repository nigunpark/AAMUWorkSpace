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
	

	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(){
		//list=占쌜쇽옙占쏙옙 占쌜듸옙
		List<CommuDTO> list = commuService.commuSelectList();
		//占쌘몌옙트 dto 확占쏙옙占쏙옙占쏙옙 -> 占쏙옙占쏙옙트占싹놂옙占쏙옙 占싱아놂옙占쏙옙 
		for(CommuDTO dto : list) {//占쏙옙 占싹놂옙占쏙옙 占싱아쇽옙 dto占쏙옙 占쏙옙占�
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			dto.setPhoto(commuService.commuSelectPhotoList(dto.getLno()));
		}
		return list;
	}////////////////commuSelectList
	

	//글 생성용
	@PostMapping(value="/gram/edit")
	public Map commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		//서버의 물리적 경로 얻기
		String path=req.getSession().getServletContext().getRealPath("/resources/upload");
		
		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			System.out.println("uploads:"+uploads);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=commuService.commuInsert(map);
		//affected占쏙옙 1占싱몌옙 占싼억옙占쏙옙占쏙옙占� result占쏙옙占� 키占쏙옙 true占쏙옙환 占싣니몌옙 false占쏙옙환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}////////////////////////////commuInsert
	
	//!!!!!!!!!글 생성용_장소 뿌려주기
	@GetMapping("/gram/place/selectList")
	public List<String> commuPlaceList(@RequestParam Map map) {
		System.out.println("map:"+map);
		List<String> list=commuService.commuPlaceList(map);
		System.out.println("list:"+list);
		return list;
	}
	
	//글 수정용
	@PutMapping("/gram/edit/{lno}")
	public CommuDTO commuUpdate(@PathVariable String lno, @RequestBody CommuDTO dto) {
		dto.setTitle(lno);
		dto.setContent(lno);
		dto.setPlace(lno);
		commuService.commuUpdate(dto);
		
		return dto;
		
	}
	
	
	
	
	
	
	
	
		

}
