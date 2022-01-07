package com.metanet.ljh.vo;

public class UserVo {
	
	private int no;
	private String name;
	private String email;
	private String password;
	private String gender;
	
	public UserVo() {
		super();
	}
	public UserVo(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserVo [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}
	public UserVo(int no, String name, String email, String gender, String password) {
		super();
		this.no = no;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.password = password;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
}
