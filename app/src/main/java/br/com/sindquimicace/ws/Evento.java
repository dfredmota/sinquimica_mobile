package br.com.sindquimicace.ws;

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
	
	private String local;
	
	private List<ParticipanteEvento> participantes;
	
	private Integer idUsuarioConfirmou;
	
	private Boolean confirmou;
	
	private Boolean visualizou;
	
	private Boolean status;
	
	private byte[] imagem;
	
	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getIdUsuarioConfirmou() {
		return idUsuarioConfirmou;
	}

	public void setIdUsuarioConfirmou(Integer idUsuarioConfirmou) {
		this.idUsuarioConfirmou = idUsuarioConfirmou;
	}

	public Boolean getConfirmou() {
		return confirmou;
	}

	public void setConfirmou(Boolean confirmou) {
		this.confirmou = confirmou;
	}

	public Boolean getVisualizou() {
		return visualizou;
	}

	public void setVisualizou(Boolean visualizou) {
		this.visualizou = visualizou;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

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
