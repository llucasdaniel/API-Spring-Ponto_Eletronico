package com.lucas.pontoeletronico.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private Date dateCreation;
	private Date dateUpdate;
	private List<Funcionario> funcionarios;

	public Empresa() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "razao_soial", nullable = false)
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@Column(name = "date_Creation", nullable = false)
	public Date getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	@Column(name="date_update",nullable=false)
	public Date getDateUpadate() {
		return this.dateUpdate;
	}
	
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate= dateUpdate;
	}
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setLancamentos(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PreUpdate
	public void preUpdate() {
		dateUpdate = new Date();
	}
	
	@PreUpdate
	public void prePersist() {
		final Date atual = new Date();
		dateCreation = atual;
		dateUpdate = atual;
	}
	
	@Override
	public  String toString() {
		return "Empresa[id="+id+",razaoSocial="+razaoSocial +
				",CNPJ="+cnpj + ",dataCriacao="+dateCreation +
				", dataAtualizacao="+ dateUpdate+"]";
	}
}
