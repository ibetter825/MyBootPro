package com.mypro.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mypro.bean.constant.WebConstant;
import com.mypro.bean.enums.ResultMessageEnum;
import com.mypro.bean.model.ResultModel;
import com.mypro.common.utils.WebUtil;

/**
 * 全局异常管理
 * 不会与springboot的BasicErrorController发生冲突
 * 只能处理异常，不能对404等错误进行管理
 * @author user
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	public static final String DEFAULT_ERROR_PATH = "/error";
	public static final String ADMIN_ERROR_PATH = "/admin/error";
	
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {
    	if(WebUtil.isAjax(req)){
			PrintWriter writer = null;
			try {
				//resp.reset();
				writer = resp.getWriter();
				resp.setStatus(HttpStatus.OK.value());
				resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
				resp.setCharacterEncoding(WebConstant.DEFAULT_ENCODING); //避免乱码
				resp.setHeader("Cache-Control", "no-cache, must-revalidate");
				writer.print(JSON.toJSONString(new ResultModel(ResultMessageEnum.OPTION_EXCEPTION)));
				writer.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if(writer != null) writer.close();
			}
		}else{
	        ModelAndView mav = new ModelAndView();
	        mav.addObject("exception", e);
	        String uri = req.getRequestURI();
	        mav.addObject("url", req.getRequestURL());
	        if(uri.startsWith("/admin"))
	        	mav.setViewName(ADMIN_ERROR_PATH+"/500");
	        else
	        	mav.setViewName(DEFAULT_ERROR_PATH+"/500");
	        return mav;
		}
        return null;
    }
}