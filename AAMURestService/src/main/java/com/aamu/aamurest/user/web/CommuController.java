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
	
	//��Ͽ�
	@GetMapping("/gram/selectList")
	public List<CommuDTO> commuSelectList(){
		//list=�ۼ��� �۵�
		List<CommuDTO> list = commuService.commuSelectList();
		//�ڸ�Ʈ dto Ȯ������ -> ����Ʈ�ϳ��� �̾Ƴ��� 
		for(CommuDTO dto : list) {//�� �ϳ��� �̾Ƽ� dto�� ���
			dto.setCommuComment(commuService.commuCommentSelectOne(dto.getLno()));
			dto.setPhoto(commuService.commuSelectPhotoList(dto.getLno()));
		}
		
		return list;
	}
	
	/*
	///�� ��Ͽ�_��� �ϳ� �������¿�
	@GetMapping("/gram/comment/selectOne/{cno}")
	public CommuCommentDTO commuCommentSelectOne(@PathVariable String cno) {
		return commuService.commuCommentSelectOne(cno);
	}
	*/
	
	//�� ������
	@PostMapping(value="/gram/edit", produces = "application/json;charset=UTF-8")
	public Map commuInsert(@RequestParam List<MultipartFile> multifiles, @RequestParam Map map, HttpServletRequest req) {
		//1]������ ������ ��� ���
		String path=req.getSession().getServletContext().getRealPath("/resources/upload");
		
		try {
			List<String> uploads= FileUploadUtil.fileProcess(multifiles, path);
			System.out.println("uploads:"+uploads);
			map.put("photolist", uploads);
		} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		
		int affected=commuService.commuInsert(map);
		//affected�� 1�̸� �Ѿ������ result��� Ű�� true��ȯ �ƴϸ� false��ȯ
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}
	
	
	
		

}
