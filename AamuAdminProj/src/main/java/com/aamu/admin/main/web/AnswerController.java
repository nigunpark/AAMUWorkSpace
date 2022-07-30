package com.aamu.admin.main.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.admin.main.service.AnswerDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.serviceimpl.AnswerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/qna/answer/")
public class AnswerController {

	@Autowired
	private AnswerServiceImpl answerService;

	@PostMapping(value = "Write.do")
	public Map write(@ModelAttribute("id") String id, @RequestParam Map map) {

		map.put("id", "ADMIN2");
		// 서비스 호출
		int newAno = answerService.qnaWrite(map);
		map.put("Ano", newAno);
		String name = answerService.findNameByKey(map);
		map.put("name", name);
		// 데이타 반환]
		// 1.String반환시
		// return String.format("{\"lno\":\"%s\",\"name\":\"%s\"}",newLno,name);
		// 2.MAP반환시
		return map;
	}//////////// write

	@PostMapping(value = "List.do", produces = "text/plain; charset=UTF-8")
	public Map list(@ModelAttribute("id") String id, @RequestParam Map map) throws JsonProcessingException {
		// 서비스 호출
		ListPagingData<AnswerDTO> answerPagingData = answerService.selectList(map, null, 0);

		/*
		 * ※JACKSON이 List<LineCommentDTO>을 JSON형태 문자열로 변경시 날짜 데이타를 2021-11-23
		 * 12:34:49.0에서 1637638489000로 변경해버린다. 숫자형태의 날짜를 년월일형태의 문자열로 변경해서 다시 Map에 해당
		 * 키값으로 저장해여한다.
		 */
		// ObjectMapper의 writeValueAsString(리스트계열 컬렉션<맵 혹은 DTO>)은
		// 리스트계열 컬렉션<맵 혹은 DTO>를 JSON형식의 문자열로 변환해준다.
		// 데이타 반환
		System.out.println(map);
		return map;
	}
	
	@PostMapping(value="Edit.do")
	public Map edit(@ModelAttribute("id") String id,@RequestParam Map map) {
		//서비스 호출
		answerService.answerUpdate(map);
		
		//데이타 반환]		
		return map;
	}

	@PostMapping(value = "Delete.do")
	public Map delete(@ModelAttribute("id") String id, @RequestParam Map map) {

		// 서비스 호출
		answerService.answerDelete(map);
		// 데이타 반환]
		return map;
	}//////////// write
	
	
	//댓글 삭제
	@PostMapping("AnswerAllDelete.do")
	@ResponseBody
	public Map answerAllDelete(@RequestBody Map map){
		int affected=answerService.answerAllDelete(map);
		//데이타 반환
		Map resultMap = new HashMap();
		System.out.println("affected:"+affected);
		if(affected==1) resultMap.put("result", "Success");
		else resultMap.put("result", "NotSuccess");
		return resultMap;
	}
	

}