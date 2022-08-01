package com.aamu.aamurest.websocket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aamu.aamurest.user.service.ChatRoomDTO;
import com.aamu.aamurest.user.service.ChatingMessageDTO;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
    public List<ChatRoomDTO> rooms(@RequestParam Map map,HttpServletRequest req){
		List<ChatRoomDTO> rooms = new Vector<>();
		rooms = template.selectList("chatroomslist", map);
		for(ChatRoomDTO dto : rooms) {
			dto.setFrompro(FileUploadUtil.requestOneFile(dto.getFrompro(), "/resources/userUpload", req));
			dto.setTopro(FileUploadUtil.requestOneFile(dto.getTopro(), "/resources/userUpload", req));
		}
        return rooms;
    }
	
	//채팅방 개설
	@PostMapping(value = "/room")
    public Map create(@RequestParam Map map){
		try {
			int roomno = template.selectOne("chatroomsone",map);
			map.put("roomno", roomno);
		}
		catch(NullPointerException e) {
			template.insert("chatroominsert",map);
		}
		Map returnMap = new HashMap();
		returnMap.put("roomno", map.get("roomno"));
		returnMap.put("list", template.selectList("chatroomslistone", map));
        return returnMap;
    }
	
	//채팅방 조회
	@GetMapping("/room")
    public List<ChatingMessageDTO> getRoom(@RequestParam Map map,HttpServletRequest req){
		
		List<ChatingMessageDTO> rooms = new Vector<>();
		rooms = template.selectList("chatroomslistone", map);
		for(ChatingMessageDTO dto : rooms) {
			dto.setAuthpro(FileUploadUtil.requestOneFile(dto.getAuthpro(), "/resources/userUpload", req));
		}
        return rooms;
    }
}
