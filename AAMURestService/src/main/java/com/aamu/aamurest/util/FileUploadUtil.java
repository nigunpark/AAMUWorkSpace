package com.aamu.aamurest.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static List<String> fileProcess(List<MultipartFile> multipartFile,String pathString) throws IllegalStateException, IOException{
	        List<String> fileList = new ArrayList<String>();
	        
	        for(MultipartFile multiFile : multipartFile ) {
	        	File file = new File(pathString+File.separator+multiFile.getOriginalFilename());
	        	multiFile.transferTo(file);
	        	fileList.add(multiFile.getOriginalFilename());
	        }
	        return fileList;
	    }
}
