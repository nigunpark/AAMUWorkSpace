package com.aamu.aamurest.user.web;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aamu.aamurest.user.service.CommuCommentDTO;
import com.aamu.aamurest.user.service.CommuDTO;
import com.aamu.aamurest.user.serviceimpl.CommuServiceImpl;

@RestController
@CrossOrigin(origins="*")
public class CommuController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommuServiceImpl commuService;
	
	//목록용
	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(){
		//list=작성한 글들
		List<CommuDTO> list = commuService.commuSelectList();
		//코맨트 dto 확장포문 -> 리스트하나씩 뽑아내서 
		for(CommuDTO dto : list) {//글 하나씩 뽑아서 dto에 담기
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			dto.setPhoto(commuService.commuSelectPhotoList(dto.getLno()));
		}
		
		return list;
	}
	
	//댓글 하나 가져오는용
	@GetMapping("/gram/comment/selectOne/{cno}")
	public CommuCommentDTO commuCommentSelectOne(@PathVariable String cno) {
		return commuService.commuCommentSelectOne(cno);
	}
	
	//글 생성용
	@PostMapping("/gram/edit")
	public Map commuInsert(@RequestPart Map map, @RequestPart MultipartHttpServletRequest mhsr) throws IllegalStateException, IOException {
		//1]서버의 물리적 경로 얻기		
		String path=mhsr.getSession().getServletContext().getRealPath("/resources/upload");
		//1-1]MultipartHttpServletRequest객체의 getFile("파라미터명")메소드로 MultipartFile객체 얻기
		List<MultipartFile> uploads= mhsr.getFiles("upload");
		for(MultipartFile upload:uploads) {
			//2]File객체 생성	
			//2-1] 파일 중복시 이름 변경
			String rename=FileUploadUtils.getNewFileName(path, upload.getOriginalFilename());
			File dest = new File(path+File.separator+rename);
			//3]업로드 처리
			upload.transferTo(dest);
			String photo = "192.168.0.5:8080/aamurest"+dest;
		}
		//localhost:
		
		int affected=commuService.commuInsert(map);
		//affected가 1이면 넘어왔으면 result라는 키로 true반환 아니면 false반환
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}
	
	
	
		

}
