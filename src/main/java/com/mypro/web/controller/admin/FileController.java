package com.mypro.web.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mypro.bean.constant.PropsConstant;
import com.mypro.configure.properties.MyPropertiesConfig;

@Controller
public class FileController extends BaseAdminController {
	
	/**
	 * 上传文件
	 * @param name 上传的文件名
	 * @param file 上传的文件
	 * @return
	 */
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file){
		if (!file.isEmpty()) {
			String rootPath = getContextPath("/");
			String uploadPath = MyPropertiesConfig.getProperty(PropsConstant.UPLOAD_ROOT_PATH);
			String filePath = rootPath + File.separator + uploadPath + File.separator;
			String fileName = file.getOriginalFilename();
            try {  
                byte[] bytes = file.getBytes();  
                File uploadFile = new File(filePath);
                if(!uploadFile.exists())
                	uploadFile.mkdirs();
                uploadFile = new File(filePath + fileName);
                BufferedOutputStream stream =  
                        new BufferedOutputStream(new FileOutputStream(uploadFile));  
                stream.write(bytes);  
                stream.close();  
                return "You successfully uploaded " + fileName + " into " + filePath + fileName;  
            } catch (Exception e) {  
            	e.printStackTrace();
                return "You failed to upload " + fileName + " => " + e.getMessage();  
            }  
        } else {  
            return "You failed to upload because the file was empty.";  
        }  
	}
}
