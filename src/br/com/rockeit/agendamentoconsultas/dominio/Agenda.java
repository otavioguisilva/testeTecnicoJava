package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.MaskFormatter;

import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import br.com.rockeit.agendamentoconsultas.utils.FactoryEntityManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

@Entity
@Table(name = "agenda")
@NamedQueries({ 
	@NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a WHERE a.dataConsulta > current_date OR "
			+ "(a.dataConsulta = current_date and a.horaConsulta >= current_time) "),
	@NamedQuery(name = "Agenda.findAllAberto", query = "SELECT a FROM Agenda a WHERE (a.dataConsulta > current_date OR "
			+ "(a.dataConsulta = current_date and a.horaConsulta >= current_time)) and a.statusCodigo in ('A','R','N') "),
	@NamedQuery(name = "Agenda.codigoCliente", query = "SELECT a FROM Agenda a WHERE a.codigoCliente = ?1"),
	@NamedQuery(name = "Agenda.dentista", query = "SELECT a FROM Agenda a WHERE a.dentista = ?1"),
	@NamedQuery(name = "Agenda.login", query = "SELECT a FROM Agenda a WHERE a.login = ?1"),
	@NamedQuery(name = "Agenda.dataConsulta", query = "SELECT a FROM Agenda a WHERE a.dataConsulta = ?1"),
	@NamedQuery(name = "Agenda.horaConsulta", query = "SELECT a FROM Agenda a WHERE a.horaConsulta = ?1"),
	@NamedQuery(name = "Agenda.statusCodigo", query = "SELECT a FROM Agenda a WHERE a.statusCodigo = ?1"),
	})
public class Agenda implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "age_codigo")
	private int codigo;
	
	@Column(name = "age_clicodigo", nullable = false, updatable = false, insertable = false)
	private int codigoCliente;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "age_clicodigo")
	private Cliente cliente;
	
	@Column(name = "age_dentista", nullable = false, updatable = false, insertable = false)
	private String dentista;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "age_dentista")
	private Usuario usuarioDentista;
	
	@Column(name = "age_usrlogin", nullable = false, updatable = false, insertable = false)
	private String login;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "age_usrlogin")
	private Usuario usuarioLogin;
	
	@Column(name = "age_dataconsulta")
	@Temporal(TemporalType.DATE)
	private Date dataConsulta;
	
	@Column(name = "age_horaconsulta")
	@Temporal(TemporalType.TIME)
	private Date horaConsulta;
	
	@Column(name = "age_stacodigo", nullable = false, updatable = false, insertable = false)
	private String statusCodigo;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "age_stacodigo")
	private StatusAgendamento statusAgendamento;
	
	@Column(name = "age_observacao")
	private String observacao;
	
	public Agenda() {
		
	}
	
	public Agenda(Cliente cliente, Usuario dentista, Usuario usuarioLogin, Date data,
			Date hora, StatusAgendamento status, String observacao) {
		this.cliente = cliente;
		this.codigoCliente = cliente.getCodigo();
		this.dentista = dentista.getLogin();
		this.usuarioDentista = dentista;
		this.usuarioLogin = usuarioLogin;
		this.login = usuarioLogin.getLogin();
		this.dataConsulta = data;
		this.horaConsulta = hora;
		this.statusCodigo = statusAgendamento.getStatusCodigo();
		this.statusAgendamento = status;
		this.observacao = observacao;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigoCliente() {
		return this.codigoCliente;
	}
	
	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getDentista() {
		return this.dentista;
	}
	
	public void setDentista(String dentista) {
		this.dentista = dentista;
	}
	
	public Usuario getUsuarioDentista() {
		return this.usuarioDentista;
	}
	
	public void setUsuarioDentista(Usuario usuario) {
		this.usuarioDentista = usuario;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Usuario getUsuarioLogin() {
		return this.usuarioLogin;
	}
	
	public void setUsuarioLogin(Usuario usuario) {
		this.usuarioLogin = usuario;
	}
	
	public Date getDataConsulta() {
		return this.dataConsulta;
	}
	
	public void setDataConsulta(Date data) {
		this.dataConsulta = data;
	}
	
	public Date getHoraConsulta() {
		return this.horaConsulta;
	}
	
	public void setHoraConsulta(Date hora) {
		this.horaConsulta = hora;
	}
	
	public String getStatusCodigo() {
		return this.statusCodigo;
	}
	
	public void setStatusCodigo(String status) {
		this.statusCodigo = status;
	}
	
	public StatusAgendamento getStatusAgendamento() {
		return this.statusAgendamento;
	}
	
	public void setStatusAgendamento(StatusAgendamento status) {
		this.statusAgendamento = status;
	}
	
	public String getObservacao() {
		return this.observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public static List<Agenda> recuperar() {
		List<Agenda> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(Agenda.class, "Agenda.findAll");
		
		return result;
	}
	
	public static boolean insereRegistro(Agenda agenda) {
		
		Agenda agendaBanco = null;
		EntityManagerFactory factory = Persistence.
	              createEntityManagerFactory("agenda");
		
		EntityManager manager = factory.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			agendaBanco = manager.merge(agenda);
			manager.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			AlertFactory.showException(e);
			manager.getTransaction().rollback();
		} finally {
			manager.close();
			factory.close();
		}
		
		return (agendaBanco != null);
	}
	
	public static boolean alteraRegistro(Agenda agenda) {
		Agenda agendaBanco = null;
		EntityManagerFactory factory = Persistence.
	              createEntityManagerFactory("agenda");
		
		EntityManager manager = factory.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			agendaBanco = manager.merge(agenda);
			manager.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			AlertFactory.showException(e);
			manager.getTransaction().rollback();
		} finally {
			manager.close();
			factory.close();
		}
		
		return (agendaBanco != null);
	}
	
	
	public static String formatarCampos(String string, String mask) throws java.text.ParseException {
        MaskFormatter mf = new MaskFormatter(mask);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(string);
    }
	
	public static void gerarColunasAgenda(TableView<Agenda> tableView) {
		TableColumn<Agenda, String> colunaDataConsulta = criarColunaTableView(
				"Data Ag." , 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getDataConsulta()));

						} catch (Exception e) {
							
						}
						return null;
					}
				}, TextFieldTableCell.<Agenda>forTableColumn());

		TableColumn<Agenda, String> colunaHoraConsulta = criarColunaTableView(
				"Hora Ag.", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getHoraConsulta()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());


		TableColumn<Agenda, String> colunaCpfCliente = criarColunaTableView(
				"CPF", 150.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(
									formatarCampos(String.valueOf(param.getValue().getCliente().getCpf()), "###.###.###-##")
									);

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());

		TableColumn<Agenda, String> colunaNomeCliente = criarColunaTableView(
				"Nome", 300.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getCliente().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());
		
		TableColumn<Agenda, String> colunaTelefoneCliente = criarColunaTableView(
				"Telefone", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(
									formatarCampos(String.valueOf(param.getValue().getCliente().getCelular()),"(##)#####-####")
									);

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());
		
		TableColumn<Agenda, String> colunaDentista = criarColunaTableView(
				"Dentista", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getUsuarioDentista().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());
		

		TableColumn<Agenda, String> colunaObservacao = criarColunaTableView(
				"Observacao", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Agenda, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Agenda, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getObservacao()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Agenda>forTableColumn());
		
		tableView.getColumns().clear();
		tableView.getColumns().addAll(colunaDataConsulta, colunaHoraConsulta, colunaCpfCliente, colunaNomeCliente, colunaTelefoneCliente, colunaDentista, colunaObservacao);
	}
	
	private static <S, T> TableColumn<S, T> criarColunaTableView(String nomeColuna, double tamanho, boolean editavel,
			Callback<CellDataFeatures<S, T>, ObservableValue<T>> propertyValueFactory,
			Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory) {

		TableColumn<S, T> coluna = new TableColumn<S, T>(nomeColuna);

		coluna.setPrefWidth(tamanho);
		coluna.setMinWidth(tamanho);
		coluna.setEditable(editavel);
		coluna.setCellValueFactory(propertyValueFactory);
		coluna.setCellFactory(cellFactory);

		return coluna;
	}

}
