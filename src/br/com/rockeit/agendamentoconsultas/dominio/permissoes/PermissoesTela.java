package br.com.rockeit.agendamentoconsultas.dominio.permissoes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissoestela")
public class PermissoesTela implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "pet_percodigo")
	private int codigoPermissao;
	
	@Id
	@Column(name = "pet_telcodigo")
	private String tela;
	
	public PermissoesTela() {		
	}
	
	public int getCodigoPermissao() {
		return this.codigoPermissao;
	}
	
	public void setCodigoPermissao(int codigo) {
		this.codigoPermissao = codigo;
	}
	
	public String getTela() {
		return this.tela;
	}
	
	public void setTela(String tela) {
		this.tela = tela;
	}

}
