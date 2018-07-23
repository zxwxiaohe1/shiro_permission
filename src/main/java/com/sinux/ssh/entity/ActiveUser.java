package com.sinux.ssh.entity;


/**
 * 用户身份信息
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;// 用户id（主键）
	private String usercode;// 用户账号
	private String username;// 用户名称

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
