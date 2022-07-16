package com.aamu.aamurest.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			String requesturl = request.getRequestURL().toString().replace(request.getRequestURI(), "/aamurest"+pathString);
			filePath.add(requesturl+File.separator+filename);
		}
		return filePath;
	}
	public static String oneFile(MultipartFile multipartFile,String path) throws IllegalStateException, IOException {
		UUID uuid = UUID.randomUUID();
    	String filename = uuid.toString()+"_"+multipartFile.getOriginalFilename();
    	File file = new File(path+File.separator+filename);
    	multipartFile.transferTo(file);
    	
    	return filename;
	}
	public static String requestOneFile(String filename,String path,HttpServletRequest req){
		
		String requesturl = req.getRequestURL().toString().replace(req.getRequestURI(), "/aamurest"+path)+File.separator+filename;

		return requesturl;
	}

}
