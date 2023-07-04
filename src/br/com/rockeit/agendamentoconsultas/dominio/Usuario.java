package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.rockeit.agendamentoconsultas.dominio.permissoes.PermissoesUsuario;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import br.com.rockeit.agendamentoconsultas.utils.FactoryEntityManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

@Entity
@Table(name = "usuario")
@NamedQueries({ 
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
	@NamedQuery(name = "Usuario.findAllDentista", query = "SELECT u FROM Usuario u WHERE u.cargoCodigo in (SELECT c.codigo from Cargo c where c.dentista=true)"),
	@NamedQuery(name = "Usuario.login", query = "SELECT u FROM Usuario u WHERE LOWER(u.login) = ?1"),
	@NamedQuery(name = "Usuario.loginDentista", query = "SELECT u FROM Usuario u WHERE LOWER(u.login) = ?1 AND u.cargoCodigo in (SELECT c.codigo from Cargo c where c.dentista=true)")
	})
public class Usuario implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "usr_login", unique = true, nullable = false, length = 30)
	private String login;
	
	@Column(name = "usr_senha", nullable = false, length = 250)
	private String senha;
	
	@Column(name = "usr_altsenhalogin", nullable = false)
	private boolean alteraSenhaLogin = true;
	
	@Column(name = "usr_nome", nullable = false, length = 20)
	private String nome;
	
	@Column(name = "usr_sobrenome", length = 40)
	private String sobreNome;
	
	@Column(name = "usr_datanasc")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(name = "usr_carcodigo", updatable = false, insertable = false)
	private int cargoCodigo;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "usr_carcodigo")
	private Cargo cargo;
	
	@Column(name = "usr_cpf", length = 11)
	private String cpf;
	
	@Column(name = "usr_cep", length = 8)
	private String cep;
	
	@Column(name = "usr_ufsigla", length = 2)
	private String siglaEstado;
	
	@Column(name = "usr_logradouro")
	private String logradouro;
	
	@Column(name = "usr_cidade", length = 40)
	private String cidade;
	
	@Column(name = "usr_bairro", length = 40)
	private String bairro;
	
	@Column(name = "usr_email", length = 250)
	private String email;
	
	@Column(name = "usr_celular", length = 11)
	private String celular;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PermissoesUsuario> permissoesUsuario;
	
	public Usuario() {
		
	}
	
	public Usuario(String login, String senha, boolean alteraSenha, String nome, String sobreNome,
			Date dataNascimento, Cargo cargo, String cpf, String cep, String siglaEstado,
			String logradouro, String cidade, String bairro, String email, String celular) {
		this.login = login;
		this.senha = senha;
		this.alteraSenhaLogin = alteraSenha;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.dataNascimento = dataNascimento;
		this.cargo = cargo;
		this.cargoCodigo = cargo.getCodigo();
		this.cpf = cpf;
		this.cep = cep;
		this.siglaEstado = siglaEstado;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.bairro = bairro;
		this.email = email;
		this.celular = celular;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean getAlteraSenhaLogin() {
		return this.alteraSenhaLogin;
	}
	
	public void setAlteraSenhaLogin(boolean altera) {
		this.alteraSenhaLogin = altera;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSobreNome() {
		return this.sobreNome;
	}
	
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	
	public Date getDataNascimento() {
		return this.dataNascimento;
	}
	
	public void setDataNascimento(Date data) {
		this.dataNascimento = data;
	}
	
	public int getCargoCodigo() {
		return this.cargoCodigo;
	}
	
	public void setCargoCodigo(int codigo) {
		this.cargoCodigo = codigo;
	}
	
	public Cargo getCargo() {
		return this.cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public String getCpf() {
		return this.cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCep() {
		return this.cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getSiglaEstado() {
		return this.siglaEstado;
	}
	
	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
	
	public String getLogradouro() {
		return this.logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getCidade() {
		return this.cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getBairro() {
		return this.bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCelular() {
		return this.celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public List<PermissoesUsuario> getPermissoesUsuario() {
		return permissoesUsuario;
	}

	public void setPermissoesUsuario(List<PermissoesUsuario> permissoesUsuario) {
		this.permissoesUsuario = permissoesUsuario;
	}
	
	public static Usuario logar(String login) {
		
		Usuario usuario = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		usuario = factory.findNamedQuerySingleResult(Usuario.class,"Usuario.login", login.toLowerCase());
		
		return usuario;
		
		
	}
	
	public static boolean alteraSenha(Usuario usuario) {
		
		EntityManagerFactory factory = Persistence.
	              createEntityManagerFactory("agenda");
		
		EntityManager manager = factory.createEntityManager();
		
		Usuario usuarioAtualizado = null;
		
		try {
			manager.getTransaction().begin();
			usuarioAtualizado = (Usuario) manager.merge(usuario);
			manager.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			AlertFactory.showException(e);
			manager.getTransaction().rollback();
		} finally {
			manager.close();
			factory.close();
		}
		
		return (usuarioAtualizado.getLogin() == usuario.getLogin() && usuarioAtualizado.getSenha() == usuario.getSenha());
	}
	
	public static Usuario recuperarUnico(String login) {
		Usuario result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuerySingleResult(Usuario.class, "Usuario.login", login.toLowerCase());
		
		return result;
	}
	
	public static Usuario recuperarUnicoDentista(String login) {
		Usuario result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuerySingleResult(Usuario.class, "Usuario.loginDentista", login.toLowerCase());
		
		return result;
	}
	
	public static List<Usuario> recuperar() {
		List<Usuario> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(Usuario.class, "Usuario.findAll");
		
		return result;
	}
	
	public static List<Usuario> recuperarDentista() {
		List<Usuario> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(Usuario.class, "Usuario.findAllDentista");
		
		return result;
	}
	
	public static void gerarColunasUsuario(TableView<Usuario> tableView) {
		TableColumn<Usuario, String> colunaLogin = criarColunaTableView(
				"Login" , 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(param.getValue().getLogin());

						} catch (Exception e) {
							
						}
						return null;
					}
				}, TextFieldTableCell.<Usuario>forTableColumn());

		TableColumn<Usuario, String> colunaNome = criarColunaTableView(
				"Nome", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Usuario>forTableColumn());


		TableColumn<Usuario, String> colunaSobreNome = criarColunaTableView(
				"SobreNome", 200.0, false,

				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getSobreNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Usuario>forTableColumn());

		TableColumn<Usuario, String> colunaEmail = criarColunaTableView(
				"Email", 300.0, false,

				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getEmail()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Usuario>forTableColumn());
		
		TableColumn<Usuario, String> colunaCargo = criarColunaTableView(
				"Cargo", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getCargo().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Usuario>forTableColumn());
		
		tableView.getColumns().clear();
		tableView.getColumns().addAll(colunaLogin, colunaNome, colunaSobreNome, colunaEmail, colunaCargo);
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
