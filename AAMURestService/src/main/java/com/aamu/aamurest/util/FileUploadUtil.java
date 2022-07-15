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
	
	public static List<byte[]> requestFilePath(List<String> filenames,String pathString,HttpServletRequest request){
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<byte[]> filePath = new Vector<byte[]>();
		for(String filename : filenames) {
			try {
		        inputStream = new FileInputStream(request.getSession().getServletContext().getRealPath(pathString+File.separator+filename));
		    }
		    catch (FileNotFoundException e) {
		    	System.out.println("파일 이름 : "+request.getSession().getServletContext().getRealPath(pathString+File.separator+filename));
		    }

		    int readCount = 0;
		    byte[] buffer = new byte[1024];
		    byte[] fileArray = null;

		    try {
		        while((readCount = inputStream.read(buffer)) != -1){
		            outputStream.write(buffer, 0, readCount);
		        }
		        fileArray = outputStream.toByteArray();
		        inputStream.close();
		        outputStream.close();

		    }
		    catch (IOException | NullPointerException e) {}
			filePath.add(fileArray);
		}
		return filePath;
	}
}
