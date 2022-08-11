package com.aamu.aamurest.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aamu.aamurest.user.service.AttractionDTO;

public class UserUtil {
	public static String changeTheme(String theme) {
		String themeid= null;
		switch(theme) {
			case "봄":
				themeid = "1";
				break;
			case "여름":
				themeid = "2";
				break;
			case "가을":
				themeid = "3";
				break;
			case "겨울":
				themeid = "4";
				break;
			case "등산/트래킹":
				themeid = "5";
				break;
			case "바다/해수욕장":
				themeid = "6";
				break;
			case "호캉스":
				themeid = "7";
				break;
			case "섬":
				themeid = "8";
				break;
			case "맛집":
				themeid = "9";
				break;
			case "힐링":
				themeid = "10";
				break;
			case "드라이브":
				themeid = "11";
				break;
		}
		return themeid;
	}
	public static int intersection(List<String> list1,List<String> list2){
		
		list1.retainAll(list2);
		return list1.size();
	}
	public static AttractionDTO changeOneAttr(AttractionDTO dto,HttpServletRequest req) {
		String title = dto.getTitle();
		if(title!=null && dto.getSmallimage()!=null) {
			if(!(dto.getSmallimage().toString().contains("http")) && dto.getSmallimage()!=null) 
				dto.setSmallimage(FileUploadUtil.requestOneFile(dto.getSmallimage(),"/resources/hotelImage",req));
			if(dto.getBigimage()==null) 
				dto.setBigimage(dto.getSmallimage());
			if(title!=null && title.contains("[") &&!(title.split("\\[")[0].equals("")))
				dto.setTitle(title.split("\\[")[0]);
			
			if(title!=null && title.contains("(")&&!(title.split("\\(")[0].equals("")))
				dto.setTitle(title.split("\\(")[0]);
		}
		return dto;
		
	}
}
