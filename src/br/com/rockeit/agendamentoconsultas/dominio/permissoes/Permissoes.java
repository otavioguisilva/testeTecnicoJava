package br.com.rockeit.agendamentoconsultas.dominio.permissoes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissoes")
public class Permissoes implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "per_codigo")
	private int codigo;
	
	@Column(name = "per_descricao")
	private String descricao;
	
	public Permissoes() {
	}
	
	public Permissoes(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
