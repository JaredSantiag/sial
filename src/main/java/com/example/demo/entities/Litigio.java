package com.example.demo.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="litigios", uniqueConstraints={@UniqueConstraint(columnNames= {"exp_jud"})})
public class Litigio{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "exp_jud")
	private String expediente;
	
	@Column(length = 80)
	private String actor;
	
	@Column(length = 80)
	private String demandado;
	
	@JsonBackReference
	@OneToMany(mappedBy="litigio",cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<Etapa> eatpas = new HashSet<>();
	
	
	public Litigio() {
		super();
	}
	
	public Litigio(Long id, String expediente, String actor, String demandado, Set<Etapa> etapas) {
		super();
		this.id = id;
		this.expediente = expediente;
		this.actor = actor;
		this.demandado = demandado;
		this.eatpas = etapas;
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

	public Set<Etapa> getEatpas() {
		return eatpas;
	}

	public void setEatpas(Set<Etapa> eatpas) {
		this.eatpas = eatpas;
	}

	
}
