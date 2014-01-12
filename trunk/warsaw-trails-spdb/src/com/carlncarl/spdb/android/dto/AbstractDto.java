package com.carlncarl.spdb.android.dto;

import java.io.Serializable;

public class AbstractDto implements Serializable{

	private static final long serialVersionUID = -7030603240190595019L;

	private String errorCode;
	
	private String errorDesc;
	
	private Object object;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
