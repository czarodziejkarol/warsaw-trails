package com.carlncarl.spdb.exception;

public class WarsawTrailsException extends Exception {

	private static final long serialVersionUID = -3923468735430524578L;
	public static final String ERROR_EXISTING_USER = "200";
	private String errorCode;

	private String errorDesc;

	public WarsawTrailsException(String errorCode, String errorDesc) {
		this.setErrorCode(errorCode);
		this.setErrorDesc(errorDesc);
	}

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

}
