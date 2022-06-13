package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;


public class EtapaDTO {
	private long id;

	@NotEmpty(message="La decripcion no puede estra vacia")
	private String descripcion;
	
	
	private Date fecha;

	public EtapaDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
