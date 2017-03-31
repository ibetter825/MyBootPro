package com.mypro.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestController;
import com.mypro.configure.security.customer.MyUserDetails;

@RestController
public class BaseController {
	@Autowired  
	protected  HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	/**
	 * 验证器存在错误消息的封装
	 * @param error
	 * @return
	 */
	public Map<String, String> resultErrors(BindingResult error){
		Map<String, String> res = null;
		if(error.hasErrors()){
			res = new HashMap<String, String>();//验证错误结果  failed:message
			List<ObjectError>  errors = error.getAllErrors();
			for(ObjectError e : errors){
				if(e instanceof FieldError)
					res.put(((FieldError) e).getField(), e.getDefaultMessage());
			}
		}
		return res;
	}
	
	/**
	 * 获取当前session中的用户
	 * @return
	 */
	public MyUserDetails getCurrentUser(){
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}
	
	/**
	 * 获取系统根目录
	 * @return
	 */
	public String getContextPath(String path){
		return request.getServletContext().getRealPath(path);
	}

	/**
	 * 添加request请求属性
	 * @param key
	 * @param val
	 */
	public void setAttr(String key, Object val){
		request.setAttribute(key, val);
	}
	/**
	 * 获取request请求属性
	 * @param key
	 * @return
	 */
	public Object getAttr(String key){
		return request.getAttribute(key);
	}
}
