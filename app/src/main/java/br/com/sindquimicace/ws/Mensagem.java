package br.com.sindquimicace.ws;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class Mensagem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8959809172509370857L;
	
	private Integer id;
	
	private String  conteudo;
	
	private Date createdAt;
	
	private Date deletedAt;
	
	private List<Usuario> usuarios;
	
	private List<Grupo> grupos;
	
	private Usuario usuario;
	
	private String  fileName;
	
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getConteudo() {
		return conteudo;
	}


	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
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


	public List<Grupo> getGrupos() {
		return grupos;
	}


	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	

}
