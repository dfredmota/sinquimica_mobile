package br.com.sindquimicace.ws;

import java.io.Serializable;
import java.util.Date;

public class TipoDocumento implements Serializable{
	
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1806684403660357843L;

	private Integer id;
	
	private String  descricao;
	
	private Date createdAt;
	
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
	
	
	

}
