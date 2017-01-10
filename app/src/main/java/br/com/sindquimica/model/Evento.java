package br.com.sindquimica.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class Evento implements Serializable{
	
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 257290762168774044L;


	private Integer id;
	
	private String  descricao;
	
	private Date    inicio;

	private Date    fim;

	private Date createdAt;

	private Integer empresaSistema;

	private List<Usuario> usuarios;
	
	private List<Grupo> grupo;
	
	private List<ParticipanteEvento> participantes;
	
	public List<ParticipanteEvento> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<ParticipanteEvento> participantes) {
		this.participantes = participantes;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Grupo> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Grupo> grupo) {
		this.grupo = grupo;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	

}
