package br.com.rockeit.agendamentoconsultas.application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.dominio.permissoes.Permissoes;
import br.com.rockeit.agendamentoconsultas.system.SystemFile;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
public class AgendamentoConsultas {
	
	private AgendamentoConsultas() {
		
	}
	
	public static void main(String[] args) {
		ApplicationFX.launch(ApplicationFX.class, args);
	}
	
	public static void close() {
		System.exit(0);
	}
}
