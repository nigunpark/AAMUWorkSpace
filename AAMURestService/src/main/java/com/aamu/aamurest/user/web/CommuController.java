package com.aamu.aamurest.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	
	//��� �ϳ� �������¿�
	@GetMapping("/gram/comment/selectOne/{cno}")
	public CommuCommentDTO commuCommentSelectOne(@PathVariable String cno) {
		return commuService.commuCommentSelectOne(cno);
	}
	
	//������
	@PostMapping("/gram/edit")
	public Map commuInsert(@RequestBody Map map) {
		int affected=commuService.commuInsert(map);
		//affected�� 1�̸� �Ѿ������ result��� Ű�� true��ȯ �ƴϸ� false��ȯ
		Map resultMap = new HashMap();
		if(affected==1) resultMap.put("result", "true");
		else resultMap.put("result", "false");
		return resultMap;
	}
	
		

}
