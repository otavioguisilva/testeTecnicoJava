package br.com.rockeit.agendamentoconsultas.negocio;

import br.com.rockeit.agendamentoconsultas.dominio.Usuario;

public class GerenteLogin {

	private static GerenteLogin gerenteLogin = null;

	private Usuario usuario = null;

	private GerenteLogin() {
	}

	public static GerenteLogin getInstance() {

		if (gerenteLogin == null) {
			gerenteLogin = new GerenteLogin();
		}
		return gerenteLogin;
	}

	public static Usuario getUsuarioLogado() {
		return getInstance().getUsuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}