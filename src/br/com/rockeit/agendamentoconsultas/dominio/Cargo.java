package br.com.rockeit.agendamentoconsultas.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
@Table(name = "cargo")
@NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")
public class Cargo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "car_codigo")
	private int codigo;
	
	@Column(name = "car_nome")
	private String nome;
	
	@Column(name = "car_observacao")
	private String observacao;
	
	@Column(name = "car_dentista")
	private boolean dentista = false;
	
	public Cargo() {
		
	}
	
	public Cargo(int codigo, String nome, String observacao) {
		this.codigo = codigo;
		this.nome = nome;
		this.observacao = observacao;
	}
	
	public Cargo(int codigo, String nome, String observacao, boolean dentista) {
		this.codigo = codigo;
		this.nome = nome;
		this.observacao = observacao;
		this.dentista = dentista;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int novoCodigo) {
		this.codigo = novoCodigo;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getObservacao() {
		return this.observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public boolean getDentista() {
		return this.dentista;
	}
	
	public void setDentista(boolean dentista) {
		this.dentista = dentista;
	}
	
	public static List<Cargo> recuperar() {
		List<Cargo> result = null;
		
		FactoryEntityManager<Cloneable> factory = new FactoryEntityManager<Cloneable>("agenda");
		
		result = factory.findNamedQuery(Cargo.class, "Cargo.findAll");
		
		return result;
	}
	
	public static void gerarColunasCargo(TableView<Cargo> tableView) {
		TableColumn<Cargo, String> colunaCodigo = criarColunaTableView(
				"Codigo" , 100.0, false,

				new Callback<TableColumn.CellDataFeatures<Cargo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>("" + param.getValue().getCodigo());

						} catch (Exception e) {
							
						}
						return null;
					}
				}, TextFieldTableCell.<Cargo>forTableColumn());

		TableColumn<Cargo, String> colunaNome = criarColunaTableView(
				"Nome", 200.0, false,

				new Callback<TableColumn.CellDataFeatures<Cargo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getNome()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cargo>forTableColumn());


		TableColumn<Cargo, String> colunaObservacao = criarColunaTableView(
				"Observação", 500.0, false,

				new Callback<TableColumn.CellDataFeatures<Cargo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
						try {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getObservacao()));

						} catch (Exception e) {
							
						}
						return null;

					}
				}, TextFieldTableCell.<Cargo>forTableColumn());

		
		
		tableView.getColumns().clear();
		tableView.getColumns().addAll(colunaCodigo, colunaNome, colunaObservacao);
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
