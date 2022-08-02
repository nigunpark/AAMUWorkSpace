package com.aamu.admin.util;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.aamu.admin.main.service.api.AttractionDTO;

public class DataUtil {
	public static List<AttractionDTO> changeAttr(List<AttractionDTO> list,HttpServletRequest req){
		List<AttractionDTO> returnList = new Vector<>();

		for(AttractionDTO dto:list) {
			String title = dto.getTitle();
			if(title!=null && dto.getSmallimage()!=null) {
				if(!(dto.getSmallimage().toString().contains("http")) && dto.getSmallimage()!=null) 
					dto.setSmallimage(FileUploadUtil.requestOneFile(dto.getSmallimage(),"/resources/hotelImage",req));
				
				if(title!=null && title.contains("[") &&!(title.split("\\[")[0].equals("")))
					dto.setTitle(title.split("\\[")[0]);
				
				if(title!=null && title.contains("(")&&!(title.split("\\(")[0].equals("")))
					dto.setTitle(title.split("\\(")[0]);

			}
			
		}
		
		return list;
	}
	public static AttractionDTO changeOneAttr(AttractionDTO dto,HttpServletRequest req) {
		String title = dto.getTitle();
		if(title!=null && dto.getSmallimage()!=null) {
			if(!(dto.getSmallimage().toString().contains("http")) && dto.getSmallimage()!=null) 
				dto.setSmallimage(FileUploadUtil.requestOneFile(dto.getSmallimage(),"/resources/hotelImage",req));
			
			if(title!=null && title.contains("\\[") &&!(title.split("\\[")[0].equals("")))
				dto.setTitle(title.split("\\[")[0]);
			
			if(title!=null && title.contains("\\(")&&!(title.split("\\(")[0].equals("")))
				dto.setTitle(title.split("\\(")[0]);
		}
		return dto;
		
	}
}
