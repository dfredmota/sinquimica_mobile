package br.com.sindquimicace.ws;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class Grupo implements Serializable{
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 7074627393779427391L;

	private Integer id;
	
	private String  nome;
	
	private Date createdAt;
	
	private Date deletedAt;
	
	private List<Usuario> usuarios;

	private List<EmpresaAssociada> empresaAssociada;

	private Integer participantes;
	
	private Integer empresaSistema;
	
	private Boolean isChecked;
		
	
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
	public List<EmpresaAssociada> getEmpresaAssociada() {
		return empresaAssociada;
	}

	public void setEmpresaAssociada(List<EmpresaAssociada> empresaAssociada) {
		this.empresaAssociada = empresaAssociada;
	}

	public Integer getEmpresaSistema() {
		return empresaSistema;
	}

	public void setEmpresaSistema(Integer empresaSistema) {
		this.empresaSistema = empresaSistema;
	}

	protected void onCreate() {
	    createdAt = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public Integer getParticipantes(){
		if(this.usuarios != null)
			return this.usuarios.size();
		else
			return new Integer(0);
	}

	public void setParticipantes(Integer participantes) {
		this.participantes = participantes;
	}
		
	
}
