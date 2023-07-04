package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.MaskFormatter;

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
@Table(name = "cliente")
@NamedQueries({ 
	@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
	@NamedQuery(name = "Cliente.codigo", query = "SELECT c FROM Cliente c WHERE c.codigo = ?1"),
	@NamedQuery(name = "Cliente.nome", query = "SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER('%?1%')"),
	@NamedQuery(name = "Cliente.sobreNome", query = "SELECT c FROM Cliente c WHERE LOWER(c.sobreNome) like LOWER('%?1%')"),
	@NamedQuery(name = "Cliente.cpf", query = "SELECT c FROM Cliente c WHERE c.cpf = ?1"),
	@NamedQuery(name = "Cliente.cep", query = "SELECT c FROM Cliente c WHERE c.cep = ?1"),
	@NamedQuery(name = "Cliente.cidade", query = "SELECT c FROM Cliente c WHERE LOWER(c.cidade) LIKE LOWER('%?1%')"),
	@NamedQuery(name = "Cliente.bairro", query = "SELECT c FROM Cliente c WHERE LOWER(c.bairro) LIKE LOWER('%?1%')"),
	@NamedQuery(name = "Cliente.email", query = "SELECT c FROM Cliente c WHERE LOWER(c.email) LIKE LOWER('%?1%')"),
	})
public class Cliente implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cli_codigo")
	private int codigo;
	
	@Column(name = "cli_nome", nullable = false, length = 20)
	private String nome;
	
	@Column(name = "cli_sobrenome", length = 40)
	private String sobreNome;
	
	@Column(name = "cli_datanasc")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(name = "cli_cpf", length = 11)
	private String cpf;
	
	@Column(name = "cli_cep", length = 8)
	private String cep;
	
	@Column(name = "cli_ufsigla", length = 2)
	private String siglaEstado;
	
	@Column(name = "cli_logradouro")
	private String logradouro;
	
	@Column(name = "cli_cidade", length = 40)
	private String cidade;
	
	@Column(name = "cli_bairro", length = 40)
	private String bairro;
	
	@Column(name = "cli_email", length = 250)
	private String email;
	
	@Column(name = "cli_celular", length = 11)
	private String celular;
	
	@Column(name = "cli_ultimaconsulta")
	@Temporal(TemporalType.DATE)
	private Date ultimaConsulta;
	
	public Cliente() {
		
	}
	
	public Cliente(String nome, String sobreNome, Date dataNascimento, String cpf, String cep, String siglaEstado,
			String logradouro, String cidade, String bairro, String email, String celular) {
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.cep = cep;
		this.siglaEstado = siglaEstado;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.bairro = bairro;
		this.email = email;
		this.celular = celular;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
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
	
	public Date getUltimaConsulta() {
		return this.ultimaConsulta;
	}
	
	public void setUltimaConsulta(Date data) {
		this.ultimaConsulta = data;
	}
	
	public static List<Cliente> recuperar() {
		List<Cliente> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(Cliente.class, "Cliente.findAll");
		
		return result;
	}
	
	public static Cliente recuperarUnicoPorCpf(String cpf) {
		
		Cliente result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuerySingleResult(Cliente.class, "Cliente.cpf", cpf);
		
		return result;
		
	}
	
	public static String formatarCampos(String string, String mask) throws java.text.ParseException {
        MaskFormatter mf = new MaskFormatter(mask);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(string);
    }
	
	public static void gerarColunasCliente(TableView<Cliente> tableView) {
		TableColumn<Cliente, String> colunaCPF = criarColunaTableView(
				"CPF" , 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(
									formatarCampos(param.getValue().getCpf(), "###.###.###-##")
									);

						} catch (Exception e) {
							
						}
						return null;
					}
				}, TextFieldTableCell.<Cliente>forTableColumn());

		TableColumn<Cliente, String> colunaNome = criarColunaTableView(
				"Nome", 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cliente>forTableColumn());


		TableColumn<Cliente, String> colunaSobreNome = criarColunaTableView(
				"SobreNome", 200.0, false,

				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getSobreNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cliente>forTableColumn());

		TableColumn<Cliente, String> colunaEmail = criarColunaTableView(
				"Email", 200.0, false,

				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getEmail()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cliente>forTableColumn());
		
		TableColumn<Cliente, String> colunaDataNascimento = criarColunaTableView(
				"Data Nascimento", 200.0, false,

				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						try {
							Date data = param.getValue().getDataNascimento();
							DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							String dataFormatada = format.format(data);
							return new ReadOnlyObjectWrapper<String>(dataFormatada);

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cliente>forTableColumn());
		
		tableView.getColumns().clear();
		tableView.getColumns().addAll(colunaCPF, colunaNome, colunaSobreNome, colunaEmail, colunaDataNascimento);
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
