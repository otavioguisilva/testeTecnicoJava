package br.com.rockeit.agendamentoconsultas.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.application.AgendamentoConsultas;
import br.com.rockeit.agendamentoconsultas.application.ApplicationFX;
import br.com.rockeit.agendamentoconsultas.dominio.Agenda;
import br.com.rockeit.agendamentoconsultas.dominio.Cargo;
import br.com.rockeit.agendamentoconsultas.dominio.Cliente;
import br.com.rockeit.agendamentoconsultas.dominio.StatusAgendamento;
import br.com.rockeit.agendamentoconsultas.dominio.Tela;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.dominio.permissoes.PermissoesUsuario;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
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

public class TelaPesquisaGenerica<T extends Cloneable> extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaPesquisaGenerica.fxml";
	
	public static final int PERMISSAO_INCLUIR = 2;
	public static final int PERMISSAO_ALTERAR = 3;
	public static final int PERMISSAO_CANCELAR = 4;
	public static final int PERMISSAO_REAGENDAR = 5;
	public static final int PERMISSAO_FINALIZAR = 6;
	public static final int PERMISSAO_EXCLUIR = 7;
	
	@FXML
	private Label lblTituloTela;
	
	@FXML
	private Label lblFiltro;
	
	@FXML
	private TextField txtFiltro;
	
	@FXML
	private TableView<T> tableResultado;
	
	@FXML
	private Button btnIncluir;
	
	@FXML
	private Button btnAlterar;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnReagendar;
	
	@FXML
	private Button btnFinalizar;
	
	@FXML
	private Button btnExcluir;
	
	@FXML
	private void onActionBtnIncluir() {
		if (classeAlvo.getName().contains("Usuario")) {
			
		};
		if (classeAlvo.getName().contains("Cargo")) {
			
		};
		if (classeAlvo.getName().contains("Agenda")) {
			abrirTelaCadastroAgenda("Inclusao", new Agenda());	
		};
		if (classeAlvo.getName().contains("Cliente")) {
			
		};
		
	}
	
	@FXML
	private void onActionBtnAlterar() {
		if (classeAlvo.getName().contains("Usuario")) {
			
		};
		if (classeAlvo.getName().contains("Cargo")) {
			
		};
		if (classeAlvo.getName().contains("Agenda")) {
			abrirTelaCadastroAgenda("Alteracao",(Agenda) tableResultado.getSelectionModel().getSelectedItem());	
		};
		if (classeAlvo.getName().contains("Cliente")) {
			
		};
		
	}
	
	@FXML
	private void onActionBtnCancelar() {
		
	}
	
	@FXML
	
	private void onActionBtnReagendar() {
		if (showYesOrNoAlert("Deseja realmente reagendar a consulta?")) {
			Agenda agenda = (Agenda) tableResultado.getSelectionModel().getSelectedItem();
			TelaReagendamento.execute(screenStage, agenda);
			tableResultado.getItems().clear();
			popularTabela();
		};
	}
	
	@FXML
	private void onActionBtnFinalizar() {
		showYesOrNoAlert("Deseja realmente finalizar a consulta");
		Agenda agenda = (Agenda) tableResultado.getSelectionModel().getSelectedItem();
		StatusAgendamento status = StatusAgendamento.recuperarUnico("F");
		agenda.setStatusAgendamento(status);
		agenda.setStatusCodigo(status.getStatusCodigo());
		Agenda.alteraRegistro(agenda);
		tableResultado.getItems().clear();
		popularTabela();
	}
	
	@FXML
	private void onActionBtnExcluir() {
		showYesOrNoAlert("Deseja realmente excluir?");
	}
	
	private Class<T> classeAlvo = null;
	
	private Tela tela;
	
	private List<PermissoesUsuario> permissoes;

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
		permissoes = PermissoesUsuario.getPermissoesTelaUsuario(gerenteLogin.getUsuario().getLogin(), tela.getCodigo());
		lblTituloTela.setText(tela.getDescricao());

		showPesquisa(classeAlvo, screenStage);
				
	}
	
	public void inativaBotoes() {
		btnIncluir.setVisible(false);
		btnAlterar.setVisible(false);
		btnCancelar.setVisible(false);
		btnReagendar.setVisible(false);
		btnFinalizar.setVisible(false);
		btnExcluir.setVisible(false);
	}
	
	public void ativaBotoes(int codigoPermissao) {
		switch(codigoPermissao) {
			case PERMISSAO_INCLUIR:
				btnIncluir.setVisible(true);
				break;
			
			case PERMISSAO_ALTERAR:
				btnAlterar.setVisible(true);
				break;
				
			case PERMISSAO_CANCELAR:
				btnCancelar.setVisible(true);
				break;
				
			case PERMISSAO_REAGENDAR:
				btnReagendar.setVisible(true);
				break;
				
			case PERMISSAO_FINALIZAR:
				btnFinalizar.setVisible(true);
				break;
				
			case PERMISSAO_EXCLUIR:
				btnExcluir.setVisible(true);
				break;
			
		}
	}
	
	public void removeBotoes() {
		if (!btnIncluir.isVisible()) {
			btnIncluir.setMaxSize(0, 0);
			btnIncluir.setPrefSize(0, 0);
			btnIncluir.setMinSize(0, 0);
			btnIncluir.setManaged(false);

		}
		if (!btnAlterar.isVisible()) {
			btnAlterar.setMaxSize(0, 0);
			btnAlterar.setPrefSize(0, 0);
			btnAlterar.setMinSize(0, 0);
			btnAlterar.setManaged(false);
		}
		if (!btnCancelar.isVisible()) {
			btnCancelar.setMaxSize(0, 0);
			btnCancelar.setPrefSize(0, 0);
			btnCancelar.setMinSize(0, 0);
			btnCancelar.setManaged(false);
		}
		if (!btnReagendar.isVisible()) {
			btnReagendar.setMaxSize(0, 0);
			btnReagendar.setPrefSize(0, 0);
			btnReagendar.setMinSize(0, 0);
			btnReagendar.setManaged(false);
		}
		if (!btnFinalizar.isVisible()) {
			btnFinalizar.setMaxSize(0, 0);
			btnFinalizar.setPrefSize(0, 0);
			btnFinalizar.setMinSize(0, 0);
			btnFinalizar.setManaged(false);
		}
		if (!btnExcluir.isVisible()) {
			btnExcluir.setMaxSize(0, 0);
			btnExcluir.setPrefSize(0, 0);
			btnExcluir.setMinSize(0, 0);
			btnExcluir.setManaged(false);
		}
	}
	
	
	public void showPesquisa(Class<T> classe, Stage primaryStage) {

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
			
			inativaBotoes();
			for(PermissoesUsuario permissao : permissoes) {
				ativaBotoes(permissao.getCodigoPermissao());
			}			
			removeBotoes();

			iniciarConfigurarController(classe);

			primaryStage.showAndWait();

	}
	
	protected void iniciarConfigurarController( Class<T> classe)  {

		iniciarConstrucaoTabela(classe);
	}
	
	public void iniciarConstrucaoTabela(Class<T> classe ) {
		
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

		ViewFactory<TelaPesquisaGenerica> factoryView = new ViewFactory<TelaPesquisaGenerica>();

		TelaPesquisaGenerica pesquisaGenerica = factoryView.getScreenControl(stage,
				TelaPesquisaGenerica.class.getResource(TELA_FXML), null, null);
		
		pesquisaGenerica.show(tela, classe);

	}
	
	private void abrirTelaCadastroAgenda(String operacao, Agenda agenda) {
		try {

			TelaCadastroAgendamento.execute(screenStage, operacao, agenda);
		} catch (Exception e) {
			e.printStackTrace();
			AlertFactory.showException(e);
		}
	}
	
}