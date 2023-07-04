package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tela")
@NamedQuery(name = "Tela.codigo", query = "SELECT t FROM Tela t WHERE t.codigo = ?1")
public class Tela implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tel_codigo")
	private String codigo;
	
	@Column(name = "tel_descricao")
	private String descricao;
	
	@Column(name = "tel_nomeimagem")
	private String nomeImagem;
	
	@Column(name = "tel_nomeclasse")
	private String nomeClasse;

	public Tela() {
	}
	
	public Tela(String codigo) {
		this.codigo = codigo;
	}
	
	public Tela(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getNomeImagem() {
		return this.nomeImagem;
	}
	
	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
	
	public String getNomeClasse() {
		return this.nomeClasse;
	}
	
	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
}
