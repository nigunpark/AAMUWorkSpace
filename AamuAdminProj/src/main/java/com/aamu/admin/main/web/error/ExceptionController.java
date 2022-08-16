package com.aamu.admin.main.web.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(Exception.class)
	public String numberFormat(Model model,Exception e) {
		model.addAttribute("errors","에러발생"+e.getMessage());

		return "redirect:/main.do";
	}

}
