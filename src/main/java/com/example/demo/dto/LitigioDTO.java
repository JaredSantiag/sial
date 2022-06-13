package com.example.demo.dto;
 
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class LitigioDTO {
	private Long id;
	
	@NotEmpty
	@Size(min=5,message="El Numero de expediente debe tener una longitud de 5 caracteres")
	private String expediente;
	
	private String actor;
	
	private String demandado;

	public LitigioDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDemandado() {
		return demandado;
	}

	public void setDemandado(String demandado) {
		this.demandado = demandado;
	}

	
}
