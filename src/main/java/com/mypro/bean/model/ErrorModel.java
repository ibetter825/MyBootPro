package com.mypro.bean.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class ErrorModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private long timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	public ErrorModel(int status, String message, String path) {
		this.timestamp = System.currentTimeMillis();
		this.status = status;
		if(status == HttpStatus.INTERNAL_SERVER_ERROR.value())
			error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		this.message = message;
		this.path = path;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
