package com.mypro.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mypro.bean.constant.WebConstant;

/**
 * 
 * @author user
 *
 */
public class WebUtil {
	/**
	 * 判断是否是Ajax请求
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String requestType = request.getHeader(WebConstant.REQUEST_TYPE);
		return requestType == null ? Boolean.FALSE : requestType.equals(WebConstant.REUQEST_TYPE_AJAX);
	}
	
	public static void writeJson(HttpServletResponse response, String json){
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding(WebConstant.DEFAULT_ENCODING);
			response.setContentType(WebConstant.JSON_CONTENT_TYPE); 
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) writer.close();
		}
	}
}
