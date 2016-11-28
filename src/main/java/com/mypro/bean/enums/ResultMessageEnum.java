package com.mypro.bean.enums;

public enum ResultMessageEnum {
	
	SESSION_TIMEOUT("会话已过期请重新登录"),
	DATA_ALREADY_EXISTS("信息已经存在你"),
	DATA_NOT_EXISTS("信息不存在或已删除"),
	OPTION_EXCEPTION("操作处理失败"),
	FILE_UPLOAD_SUCCESS("文件上传成功"),
	FILE_UPLOAD_FAIL("文件上传异常");
	
	private String msg;
	private ResultMessageEnum(String msg){
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
