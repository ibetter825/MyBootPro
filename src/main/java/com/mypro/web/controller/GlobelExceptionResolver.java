package com.mypro.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.mypro.common.utils.WebUtil;

/**
 * 全局错误解析器
 * @author user
 *
 */
@Controller
public class GlobelExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object obj,
			Exception ex) {
		if(WebUtil.isAjax(req)){
			PrintWriter writer = null;
			try {
				resp.reset();
				writer = resp.getWriter();
				resp.setStatus(403);
				resp.setContentType("text/html;charset=UTF-8");
				resp.setHeader("pragma", "no-cache");
				resp.setHeader("cache-control", "no-cache");
				writer.print(JSON.toJSONString("验证出错"));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(writer != null) writer.close();
			}
		}
		System.err.println("全局错误解析器");
		ex.printStackTrace();
		return null;//跳转到500错误页面，可以分别处理
	}
	
}
