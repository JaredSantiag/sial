package com.example.demo.dto;

public class LoginDTO {

	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String usernameOrEmail) {
		this.username = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}