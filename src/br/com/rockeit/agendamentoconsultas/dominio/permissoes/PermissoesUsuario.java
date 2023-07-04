package br.com.rockeit.agendamentoconsultas.dominio.permissoes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.rockeit.agendamentoconsultas.dominio.Tela;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.utils.FactoryEntityManager;

@Entity
@Table(name = "permissoesusuario")
@NamedQueries({
@NamedQuery(name = "PermissoesUsuario.getPermissoesConsultaUsuario", 
query = "SELECT p FROM PermissoesUsuario p WHERE LOWER(p.usuarioLogin) LIKE ?1 AND p.codigoPermissao = 1"),
@NamedQuery(name = "PermissoesUsuario.getPermissaoUsuarioTela",
query = "SELECT	p FROM PermissoesUsuario p WHERE LOWER(p.usuarioLogin) LIKE ?1 AND LOWER(p.telaCodigo) = ?2")
})
public class PermissoesUsuario implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "peu_percodigo")
	private int codigoPermissao;
	
	@ManyToOne()
	@JoinColumn(name = "peu_percodigo")
	private Permissoes permissoes;
	
	@Id
	@Column(name = "peu_telcodigo")
	private String telaCodigo;
	
	@ManyToOne()
	@JoinColumn(name = "peu_telcodigo")
	private Tela tela;
	
	@Id
	@Column(name = "peu_usrlogin")
	private String usuarioLogin;
	
	@ManyToOne()
	@JoinColumn(name = "peu_usrlogin")
	private Usuario usuario;
	
	public PermissoesUsuario() {
		
	}
	
	public PermissoesUsuario(int permissao, String tela, String login) {
		this.codigoPermissao = permissao;
		this.telaCodigo = tela;
		this.usuarioLogin = login;
	}
	
	public int getCodigoPermissao() {
		return this.codigoPermissao;
	}
	
	public void setCodigoPermissao(int codigo) {
		this.codigoPermissao = codigo;
	}
	
	public Permissoes getPermissoes() {
		return this.permissoes;
	}
	
	public void setPermissoes(Permissoes permissoes) {
		this.permissoes = permissoes;
	}
	
	public String getTelaCodigo() {
		return this.telaCodigo;
	}
	
	public void setTelaCodigo(String tela) {
		this.telaCodigo = tela;
	}
	
	public Tela getTela() {
		return this.tela;
	}
	
	public void setTela(Tela tela) {
		this.tela = tela;
	}
	
	public String getUsuarioLogin() {
		return this.usuarioLogin;
	}
	
	public void setUsuarioLogin(String login) {
		this.usuarioLogin = login;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public static List<PermissoesUsuario> getPermissoesConsultaUsuario(String usuarioLogin) {
		List<PermissoesUsuario> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(PermissoesUsuario.class, "PermissoesUsuario.getPermissoesConsultaUsuario", usuarioLogin.toLowerCase());
		
		return result;
	}
	
	public static List<PermissoesUsuario> getPermissoesTelaUsuario(String usuarioLogin, String nomeTela) {
		List<PermissoesUsuario> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(PermissoesUsuario.class, "PermissoesUsuario.getPermissaoUsuarioTela", usuarioLogin.toLowerCase(), nomeTela.toLowerCase());
		
		return result;
	}

}
