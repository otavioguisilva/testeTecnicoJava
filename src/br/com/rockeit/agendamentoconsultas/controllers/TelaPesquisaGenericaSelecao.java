package br.com.rockeit.agendamentoconsultas.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.dominio.Agenda;
import br.com.rockeit.agendamentoconsultas.dominio.Cargo;
import br.com.rockeit.agendamentoconsultas.dominio.Cliente;
import br.com.rockeit.agendamentoconsultas.dominio.Tela;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TelaPesquisaGenericaSelecao<T extends Cloneable> extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaPesquisaGenericaSelecao.fxml";
		
	@FXML
	private Label lblTituloTela;
	
	@FXML
	private Label lblFiltro;
	
	@FXML
	private TextField txtFiltro;
	
	@FXML
	private TableView<T> tableResultado;
	
	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;
	
	@FXML
	private void onActionBtnCancelar() {
	}
	
	@FXML
	private void onActionBtnConfirmar() {
		
	}

	private Class<T> classeAlvo = null;
	
	private Tela tela;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadParams(Object... objects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show(Object... objects) {	
		registerKeyEvents();
		tela = (Tela) objects[0];
		classeAlvo = (Class<T>) objects[1];
		lblTituloTela.setText(tela.getDescricao());
		
		try {
			showPesquisa(classeAlvo, screenStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
	}
	
	public void showPesquisa(Class<T> classe, Stage primaryStage) throws Exception {

		try {
			if (primaryStage.getStyle() != StageStyle.TRANSPARENT) {
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.initModality(Modality.WINDOW_MODAL);
				primaryStage.getScene().setFill(null);
				primaryStage.centerOnScreen();
				primaryStage.setResizable(false);
			}
			
			FadeTransition ft = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.setAutoReverse(true);
			ft.play();
		
			primaryStage.getScene().getRoot().setEffect(new Blend(BlendMode.COLOR_BURN));

			iniciarConfigurarController(classe);

			primaryStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void iniciarConfigurarController( Class<T> classe) throws Exception {

		iniciarConstrucaoTabela(classe);
	}
	
	public void iniciarConstrucaoTabela(Class<T> classe ) throws Exception {

		this.setClasseAlvo(classe);

		construirColunas();

		popularTabela();

	}
	
	private void construirColunas() {
		if (classeAlvo.getName().contains("Usuario")) {
			Usuario.gerarColunasUsuario((TableView<Usuario>)tableResultado);
		};
		if (classeAlvo.getName().contains("Cargo")) {
			Cargo.gerarColunasCargo((TableView<Cargo>)tableResultado);
		};
		if (classeAlvo.getName().contains("Agenda")) {
			Agenda.gerarColunasAgenda((TableView<Agenda>)tableResultado);
		};
		if (classeAlvo.getName().contains("Cliente")) {
			Cliente.gerarColunasCliente((TableView<Cliente>)tableResultado);
		};
		
	}
	
	private void popularTabela() {
		if (classeAlvo.getName().contains("Usuario")) {
			List<Usuario> usuarios = Usuario.recuperar();
			
			((TableView<Usuario>) tableResultado).setItems(FXCollections.observableArrayList(usuarios));
		};
		
		if (classeAlvo.getName().contains("Cargo")) {
			List<Cargo> cargos = Cargo.recuperar();
			
			((TableView<Cargo>) tableResultado).setItems(FXCollections.observableArrayList(cargos));
		};
		
		if (classeAlvo.getName().contains("Cliente")) {
			List<Cliente> clientes = Cliente.recuperar();
			
			((TableView<Cliente>) tableResultado).setItems(FXCollections.observableArrayList(clientes));
		};
		
		if (classeAlvo.getName().contains("Agenda")) {
			List<Agenda> agendas = Agenda.recuperar();
			
			((TableView<Agenda>) tableResultado).setItems(FXCollections.observableArrayList(agendas));
		};
	}
	
	public void closePesquisaDialog() {
		if (screenStage != null) {
			screenStage.close();
		}
	}
	
	public Class<T> getClasseAlvo() {
		return this.classeAlvo;
	}
	
	public void setClasseAlvo(Class<T> classe) {
		this.classeAlvo = classe;
	}
	
	public static void execute(Stage stage, Tela tela, Class<?> classe) {

		ViewFactory<TelaPesquisaGenericaSelecao> factoryView = new ViewFactory<TelaPesquisaGenericaSelecao>();

		TelaPesquisaGenericaSelecao pesquisaGenerica = factoryView.getScreenControl(stage,
				TelaPesquisaGenericaSelecao.class.getResource(TELA_FXML), null, null);
		
		pesquisaGenerica.show(tela, classe);

	}
	
}