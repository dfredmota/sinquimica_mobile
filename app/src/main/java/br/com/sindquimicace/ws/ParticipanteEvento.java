package br.com.sindquimicace.ws;

import java.io.Serializable;
import java.util.Date;


public class ParticipanteEvento  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8439628861178687181L;


	private Integer id;
	
	private String  nome;
	
	private String  email;	

	private Date createdAt;
	
	private Date deletedAt;
	
	private Integer empresaSistema;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
