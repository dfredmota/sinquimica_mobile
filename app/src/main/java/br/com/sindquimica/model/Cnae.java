package br.com.sindquimica.model;

import java.io.Serializable;




public class Cnae implements Serializable{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7619930467191699624L;


	private Integer id;
	
	private String codigo;
	
	private String descricao;

	private Integer empresaSistema;

	public Integer getEmpresaSistema() {
		return empresaSistema;
	}

	public void setEmpresaSistema(Integer empresaSistema) {
		this.empresaSistema = empresaSistema;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
