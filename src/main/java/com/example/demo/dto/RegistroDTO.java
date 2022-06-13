package com.example.demo.dto;

public class RegistroDTO {

	private String nombre;
	private String username;
	private String email;
	private String password;
	private Long licenciaId;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
	public Long getLicenciaId() {
		return licenciaId;
	}

	public void setLicenciaId(Long licenciaId) {
		this.licenciaId = licenciaId;
	}

	public RegistroDTO() {
		super();
	}

}