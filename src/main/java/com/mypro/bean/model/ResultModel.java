package com.mypro.bean.model;

import java.io.Serializable;
import java.util.Map;
import com.google.common.collect.Maps;
import com.mypro.bean.enums.ResultMessageEnum;
import com.mypro.common.utils.StringUtil;

/**
 * 结果
 * @author user
 *
 */
public class ResultModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作成功代码
	 */
	public static final String SUCCESS = "1";
	/**
	 * 操作成功消息
	 */
	public static final String SUCCESS_MSG = "操作成功";
	/**
	 * 操作失败消息，前缀
	 */
	public static final String FAIL_MSG_PREFIX = "操作失败：";
	/**
	 * 操作失败代码，默认为-1，<=-1都为操作失败
	 */
	public static final String FAIL = "-1";
	
	private String code = SUCCESS;//默认操作成功
	private String msg = SUCCESS_MSG;
	private Map<String, Object> data = Maps.newHashMap();
	
	public ResultModel(){}
	
	public ResultModel(String failMsg){
		this.code = FAIL;
		this.msg = failMsg;
	}
	
	public ResultModel(ResultMessageEnum messageEnum){
		this.code = FAIL;
		this.msg = StringUtil.append(FAIL_MSG_PREFIX, messageEnum.getMsg());
	}
	
	public ResultModel(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public boolean isSuccess(){
		return SUCCESS.equals(this.code);
	}
	
	
	public ResultModel setSuccessMsg(String msg){
		this.code = SUCCESS;
		this.msg = msg;
		return this;
	}
	
	public ResultModel setFailMsg(String msg){
		this.code = FAIL;
		this.msg = StringUtil.append(FAIL_MSG_PREFIX, msg);
		return this;
	}
	
	public String getCode() {
		return code;
	}
	
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public ResultModel setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}
	
}
