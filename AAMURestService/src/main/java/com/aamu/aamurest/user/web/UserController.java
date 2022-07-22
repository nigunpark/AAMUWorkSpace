package com.aamu.aamurest.user.web;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UsersService service;
	
	
	@PostMapping("/users/insert")
	public int join(@RequestParam Map map,@RequestParam MultipartFile userprofile,HttpServletRequest req) throws IllegalStateException, IOException {
		int affected=0;
		String path = req.getSession().getServletContext().getRealPath("/resources/userUpload");
		String photo = FileUploadUtil.oneFile(userprofile, path);
		
		map.put("userprofile",photo);
		
		affected = service.joinUser(map);
		
		return affected;
	}
	
	@GetMapping("/users/selectone")
	public UsersDTO selectOneUser(@RequestParam Map map,HttpServletRequest req) {
		
		UsersDTO dto = service.selectOneUser(map);
		String path = req.getSession().getServletContext().getRealPath("/resources/userUpload");
		dto.setUserprofile(FileUploadUtil.requestOneFile(dto.getUserprofile(), path, req));
		
		return dto;
	}
	
	
	@PutMapping("/users/update")
	public int updateUser(@RequestBody UsersDTO dto) {
		int affected=0;
		
		affected = service.updateUser(dto);
		
		return affected;
	}
	@GetMapping("/users/checkid")
	public int checkId(@RequestParam String id) {
		System.out.println(id);
		return service.checkId(id);
	}
	
	
}
