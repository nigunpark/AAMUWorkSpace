package com.aamu.aamurest.websocket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
    public List<ChatRoomDTO> rooms(@RequestParam Map map){
        return template.selectList("chatroomslist", map);
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
    public List<ChatingMessageDTO> getRoom(@RequestParam Map map){
        return template.selectList("chatroomslistone", map);
    }
}
