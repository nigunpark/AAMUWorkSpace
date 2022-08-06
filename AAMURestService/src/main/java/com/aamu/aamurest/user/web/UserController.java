package com.aamu.aamurest.user.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

import com.aamu.aamurest.user.service.MainService;
import com.aamu.aamurest.user.service.UsersDTO;
import com.aamu.aamurest.user.service.UsersService;
import com.aamu.aamurest.util.FileUploadUtil;
import com.aamu.aamurest.util.UserUtil;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UsersService service;

	@PostMapping("/users/edit")
	public int join(@RequestParam Map map,@RequestParam MultipartFile userprofile,@RequestParam List theme,HttpServletRequest req) throws IllegalStateException, IOException {
		System.out.println(map);
		map.put("theme", theme);
		int affected=0;
		String path = req.getSession().getServletContext().getRealPath("/resources/userUpload");
		String photo = FileUploadUtil.oneFile(userprofile, path);

		map.put("userprofile",photo);

		affected = service.joinUser(map);

		return affected;
	}
	@PostMapping("/users/upload")
	public UsersDTO updateUser(@RequestParam Map map,@RequestParam(required = false) MultipartFile userprofile,@RequestParam List theme,HttpServletRequest req) throws IllegalStateException, IOException {

		int affected=0;
		System.out.println(map);
		System.out.println(userprofile);
		
		UsersDTO dto = service.selectOneUser(map);
		String originalProfile = dto.getUserprofile();
		String path = req.getSession().getServletContext().getRealPath("/resources/userUpload");
		if(userprofile!=null) {
			File oldFile = new File(path+File.separator+originalProfile);
			oldFile.delete();
			String photo = FileUploadUtil.oneFile(userprofile, path);
			map.put("userprofile",photo);
		}
		else map.put("userprofile", originalProfile);
		map.put("themes", theme);
		affected = service.updateUser(map);
		UsersDTO userDto = service.selectOneUser(map);
		userDto.setUserprofile(FileUploadUtil.requestOneFile(dto.getUserprofile(),"/resources/userUpload", req));
		
		return userDto;
	}
	@GetMapping("/users/selectone")
	public UsersDTO selectOneUser(@RequestParam Map map,HttpServletRequest req) {

		UsersDTO dto = service.selectOneUser(map);
		dto.setUserprofile(FileUploadUtil.requestOneFile(dto.getUserprofile(), "/resources/userUpload", req));
		return dto;
	}

	@GetMapping("/users/checkid")
	public int checkId(@RequestParam String id) {
		System.out.println(id);
		return service.checkId(id);
	}
	@GetMapping("/users/theme")
	public List<Map> getTheme(HttpServletRequest req) {
		List<Map> list = service.selectAllTheme();

		for(Map map:list) {
			map.put("themeimg",FileUploadUtil.requestOneFile(map.get("THEMEIMG").toString(),"/resources/themeImage", req));
		}
		return list;
		
	}
	@GetMapping("/user/theme")
	public List selectTheme(@RequestParam Map map){
		
		return service.selectUserTheme(map);
	}
	@PutMapping("/users/updatetheme")
	public int theme(@RequestBody Map map) {
		int affected = 0;
		System.out.println(map);
		affected = service.updateTheme(map);
		
		return affected;
	}


}
