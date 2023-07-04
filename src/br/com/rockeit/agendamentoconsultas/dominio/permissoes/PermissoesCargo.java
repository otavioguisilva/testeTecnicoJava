package br.com.rockeit.agendamentoconsultas.dominio.permissoes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissoescargo")
public class PermissoesCargo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "pec_percodigo")
	private int codigoPermissao;
	
	@Id
	@Column(name = "pec_telcodigo")
	private String tela;
	
	@Id
	@Column(name = "pec_carcodigo")
	private int codigoCargo;
	
	public PermissoesCargo() {
		
	}
	
	public PermissoesCargo(int permissao, String tela, int cargo) {
		this.codigoPermissao = permissao;
		this.tela = tela;
		this.codigoCargo = cargo;
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
	
	public int getCodigoCargo() {
		return this.codigoCargo;
	}
	
	public void setCodigoCargo(int codigo) {
		this.codigoCargo = codigo;
	}

}
