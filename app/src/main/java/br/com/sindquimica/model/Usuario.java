package br.com.sindquimica.model;

import java.util.Date;
import java.util.List;




public class Usuario {

	private Integer id;
	private String nome;
	private Date dtNascimento;
	private Endereco endereco;
	private String email;
	private String telefones;
	private String site;
	private Boolean status;
	private EmpresaAssociada empresa;
    private List<Documento> listaDocumentos;
	private Date createdAt;
	private String login;
	private String password;
	private String imagemPath;
	private Boolean cadastroApp;
	private Integer empresaSistema;
	private Perfil perfil;
	private byte[] imagem;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Integer getEmpresaSistema() {
		return empresaSistema;
	}

	public void setEmpresaSistema(Integer empresaSistema) {
		this.empresaSistema = empresaSistema;
	}

	public Boolean getCadastroApp() {
		return cadastroApp;
	}

	public void setCadastroApp(Boolean cadastroApp) {
		this.cadastroApp = cadastroApp;
	}

	public String getImagemPath() {
		return imagemPath;
	}

	public void setImagemPath(String imagemPath) {
		this.imagemPath = imagemPath;
	}
	
	public String getAtivo(){
		if(this.status)
			return "Liberado";
		else
			return "Bloqueado";
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefones() {
		return telefones;
	}

	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public EmpresaAssociada getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaAssociada empresa) {
		this.empresa = empresa;
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
