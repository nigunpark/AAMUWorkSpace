package com.aamu.aamurest.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static List<String> fileProcess(List<MultipartFile> multipartFile,String pathString) throws IllegalStateException, IOException{
        List<String> fileList = new ArrayList<String>();
        
        for(MultipartFile multiFile : multipartFile ) {
        	UUID uuid = UUID.randomUUID();
        	String filename = uuid.toString()+"_"+multiFile.getOriginalFilename();
        	File file = new File(pathString+File.separator+filename);
        	multiFile.transferTo(file);
        	fileList.add(filename);
        }
        return fileList;
    }
	
	public static List<String> requestFilePath(List<String> filenames,String pathString,HttpServletRequest request){
		List<String> filePath = new Vector<String>();
		for(String filename : filenames) {
			filePath.add(request.getSession().getServletContext().getRealPath(pathString+filename));
		}
		return filePath;
	}
}
