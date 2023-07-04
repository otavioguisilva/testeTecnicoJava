package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.rockeit.agendamentoconsultas.utils.FactoryEntityManager;

@Entity
@Table(name = "statusagendamento")
@NamedQuery(name = "StatusAgendamento.findById", query = "SELECT s FROM StatusAgendamento s WHERE s.statusCodigo = ?1")
public class StatusAgendamento implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public final static String STATUS_AGENDADO = "A";
	public final static String STATUS_ATENDIDO = "F";
	public final static String STATUS_CANCELADO = "C";
	public final static String STATUS_REAGENDADO = "R";
	public final static String STATUS_NAO_COMPARECEU = "N";
	
	@Id
	@Column(name = "sta_codigo", length = 1)
	private String statusCodigo;
	
	@Column(name = "sta_desc", length = 20)
	private String descricao;
	
	public StatusAgendamento() {
	}
	
	public StatusAgendamento(String codigo){
		this.statusCodigo = codigo;
	}
	
	public StatusAgendamento(String codigo, String descricao) {
		this.statusCodigo = codigo;
		this.descricao = descricao;
	}
	
	public String getStatusCodigo() {
		return this.statusCodigo;
	}
	
	public void setStatusCodigo(String codigo) {
		this.statusCodigo = codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static StatusAgendamento recuperarUnico(String codigo) {
		StatusAgendamento result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuerySingleResult(StatusAgendamento.class, "StatusAgendamento.findById", codigo);
		
		return result;
	}
	
	public boolean isStatusAgendado() {
		return this.statusCodigo.equals(STATUS_AGENDADO);
	}
	
	public boolean isStatusAtendido() {
		return this.statusCodigo.equals(STATUS_ATENDIDO);
	}
	
	public boolean isStatusCancelado() {
		return this.statusCodigo.equals(STATUS_CANCELADO);
	}
	
	public boolean isStatusReagendado() {
		return this.statusCodigo.equals(STATUS_REAGENDADO);
	}
	
	public boolean isStatusNaoCompareceu() {
		return this.statusCodigo.equals(STATUS_NAO_COMPARECEU);
	}

}
