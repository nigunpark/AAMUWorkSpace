package com.aamu.aamurest.websocket.controller;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	
	@Autowired
	private SqlSessionTemplate template;
	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
    public ModelAndView rooms(@RequestParam Map map){
        ModelAndView mv = new ModelAndView("chat/rooms");
        //mv.addObject("list", chatRoomRepository.findAllRooms());
        return mv;
    }
	
	//채팅방 개설
	@PostMapping(value = "/room")
    public String create(@RequestParam Map map, RedirectAttributes rttr){
        //rttr.addFlashAttribute("roomName", chatRoomRepository.createChatRoom(name));
        return "redirect:/chat/rooms";
    }
	
	//채팅방 조회
	@GetMapping("/room")
    public ModelAndView getRoom(@RequestParam Map map){
        ModelAndView mv = new ModelAndView("chat/room");
        //mv.addObject("room", chatRoomRepository.findByRoomId(roomId));
        return mv;
    }
}
