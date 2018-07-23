package com.sinux.ssh.Exception;

/**
 * Description: 系统自定义的异常类型
 */
public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;
	// 异常信息
	private String message;

	public LoginException(String message) {
		super(message);
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
