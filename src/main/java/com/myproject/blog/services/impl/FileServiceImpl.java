package com.myproject.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.blog.services.FileService;

@Service 
public class FileServiceImpl implements FileService {

	@Override
	public String UploadImage(String path, MultipartFile file) throws IOException {
		
		//File Name
		String name=file.getOriginalFilename();
		//System.out.println("Line 25 "+file);
		
		//random name generate file
		String randomId=UUID.randomUUID().toString();
		//System.out.println("Line 27 "+randomId);
		
		String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));
		//System.out.println("Line 30 "+fileName1);
		
		//Full Path
		String filePath=path+File.separator+fileName1;
		//filePath=filePath.substring(0,7)+filePath.substring(11);  
		//System.out.println("Line 33 "+filePath);
		
		//Create a folder if not created 
		File f=new File(path);
		//System.out.println("Line 36"+f);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//file copy
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		//System.out.println("Line 46 "+Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}

}
