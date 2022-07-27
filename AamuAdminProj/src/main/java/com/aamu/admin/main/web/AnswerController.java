package com.aamu.admin.main.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aamu.admin.main.service.AnswerDTO;
import com.aamu.admin.main.service.ListPagingData;
import com.aamu.admin.main.serviceimpl.AnswerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SessionAttributes({"id"})
@RestController
@RequestMapping("/qna/answer/")
public class AnswerController {

	
}