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
	
	//글 목록용
	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(){
		//list=글 목록들
		List<CommuDTO> list = commuService.commuSelectList();
		for(CommuDTO dto : list) {//글 목록들 list에서 하나씩 꺼내서 dto에 담는다
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
			System.out.println("(CommuController)uploads:"+uploads);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=commuService.commuInsert(map);
		//affected 1이면 저장 잘된거
		System.out.println("(CommuController)affected:"+affected);
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}////////////////////////////commuInsert
	
	//글 생성용_장소 뿌려주기
	@GetMapping("/gram/place/selectList")
	public List<Map> commuPlaceList(@RequestParam Map map) {
		System.out.println("map:"+map);
		List<Map> list=commuService.commuPlaceList(map);
		System.out.println("list:"+list);
		return list;
	}
	
	//글 하나 뿌려주는 용
	@GetMapping("/gram/SelectOne/{lno}")
	public CommuDTO commuSelectOne(@PathVariable String lno) {
		CommuDTO dto=commuService.commuSelectOne(lno);
		//모든 댓글 가져오기
		dto.setCommuCommentList(commuService.commuCommentList(lno));
		//모든 사진 가져오기
		dto.setPhoto(commuService.commuSelectPhotoList(lno));
		return dto;
	}
	
	//글 수정용
	@PutMapping("gram/edit/{lno}")
	public CommuDTO commuUpdate(@PathVariable String lno, @RequestParam CommuDTO dto) {
		dto.setTitle(lno);
		dto.setContent(lno);
		dto.setContentid(lno);
		int affected=commuService.commuUpdate(dto);
		System.out.println(affected+"행이 수정됨");
		return dto;
	}
	
	
	
	

}
