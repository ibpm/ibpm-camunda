package com.github.ibpm.common.result.sys.user;

import lombok.ToString;

@ToString
public class User {

	protected String userName;

	protected String displayName;

	protected String password;

	protected String email;

	protected String mobile;

	protected int status;

	protected int superAdmin;

	protected String sendConfig;

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public User setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public User setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public User setStatus(int status) {
		this.status = status;
		return this;
	}

	public int getSuperAdmin() {
		return superAdmin;
	}

	public User setSuperAdmin(int superAdmin) {
		this.superAdmin = superAdmin;
		return this;
	}

	public String getSendConfig() {
		return sendConfig;
	}

	public User setSendConfig(String sendConfig) {
		this.sendConfig = sendConfig;
		return this;
	}
}
