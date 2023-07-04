package br.com.rockeit.agendamentoconsultas.controllers;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.dominio.Agenda;
import br.com.rockeit.agendamentoconsultas.dominio.Cliente;
import br.com.rockeit.agendamentoconsultas.dominio.StatusAgendamento;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class TelaCadastroAgendamento extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaCadastroAgendamento.fxml";
	
	private Stage stageOwner = null;
	
	@FXML
	TextField txtCodigo;
	
	@FXML
	TextField txtCliente;
	
	@FXML
	TextField txtDentista;
	
	@FXML
	DatePicker dpData;
	
	@FXML
	TextField txtHora;
	
	@FXML
	TextArea txtObservacao;
	
	@FXML
	Button btnConfirmar;
	
	@FXML
	Button btnCancelar;
	
	@FXML
	public boolean onClienteConfirm() {
		boolean result = false;
		String pesquisaCliente = txtCliente.getText().trim();
		if (!(pesquisaCliente == null ||pesquisaCliente.length() == 0)) {
			if (pesquisaCliente.length() == 11) {
				Cliente clientePesquisa = Cliente.recuperarUnicoPorCpf(pesquisaCliente);
				
				if (clientePesquisa == null) {
					showAlert("Atenção", "Não existe Cliente Cadastrado com esse CPF verifique");
				} else {
					result = true;
					cliente = clientePesquisa;
					txtCliente.setText(cliente.getNome());
				}
			}
		}
		return result;
	}
	
	public void onClientePesquisa() {
		
	}
	
	@FXML
	public boolean onDentistaConfirm() {
		boolean result = false;
		String pesquisaDentista = txtDentista.getText().trim();
		if (!(pesquisaDentista == null ||pesquisaDentista.length() == 0)) {
			Usuario denstistaPesquisa = Usuario.recuperarUnicoDentista(pesquisaDentista);
				
			if (denstistaPesquisa == null) {
				showAlert("Atenção", "Não existe dentista cadastrado com esse login verifique");
			} else {
				result = true;
				dentista = denstistaPesquisa;
				txtDentista.setText(dentista.getNome());
			}
		}
		return result;
	}
	
	@FXML
	public void onDataConfirm() {
		
	}
	
	@FXML
	public void onHoraConfirm() {
		
	}
	
	@FXML
	public void onActionBtnConfirmar() {
		if ((txtCliente.getText() == null || txtCliente.getText().trim().length() == 0) ||
				(txtDentista.getText() == null || txtDentista.getText().trim().length() == 0) ||
				(dpData.getValue() == null ) ||
				(txtHora.getText() == null || txtHora.getText().trim().length() == 0) 
				) {
			showAlert("Atenção", "Existem campos obrigatórios que ainda não foram preenchidos");
		} else {
			if (tipoOperacao.toLowerCase().equals("Alteracao".toLowerCase())) {
				if (showYesOrNoAlert("Confirma o alteração dessa consulta?")) {
					dentista = Usuario.recuperarUnico(txtDentista.getText());
					agenda.setDentista(dentista.getLogin());
					agenda.setUsuarioDentista(dentista);
					agenda.setObservacao(txtObservacao.getText());
					Agenda.alteraRegistro(agenda);
				};
			} else  {
				if (showYesOrNoAlert("Confirma o agendamento dessa consulta?")) {
					agenda.setCodigoCliente(cliente.getCodigo());
					agenda.setCliente(cliente);
					agenda.setDentista(dentista.getLogin());
					agenda.setUsuarioDentista(dentista);
					agenda.setLogin(gerenteLogin.getUsuario().getLogin());
					agenda.setUsuarioLogin(gerenteLogin.getUsuario());
					agenda.setDataConsulta(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
					agenda.setHoraConsulta(Time.valueOf(txtHora.getText()));
					agenda.setStatusCodigo("A");
					agenda.setObservacao(txtObservacao.getText());
					StatusAgendamento status = StatusAgendamento.recuperarUnico("A");
					agenda.setStatusAgendamento(status);
					agenda.setStatusCodigo(status.getStatusCodigo());
					if (Agenda.insereRegistro(agenda)) {
						showAlert("Agendado", "Agendamento Realizado com sucesso");
						close(false);
					};
				}; 
			} 
		}
	}
	
	@FXML
	public void onActionBtnCancelar() {
		close(true);
	}
	
	private Cliente cliente;
	
	private Usuario dentista;
	
	private Agenda agenda;
	
	private String tipoOperacao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadParams(Object... objects) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onStageClose() {
		super.onStageClose();

		Window owner = screenStage.getOwner();

		if (owner instanceof Stage) {
			((Stage) owner).getScene().getRoot().setEffect(null);
		}
	}

	@Override
	public void show(Object... objects) {
		registerKeyEvents();
		screenStage.setAlwaysOnTop(true);
		if (screenStage.getStyle() != StageStyle.TRANSPARENT) {
			screenStage.initStyle(StageStyle.TRANSPARENT);
			screenStage.initModality(Modality.WINDOW_MODAL);
			screenStage.getScene().setFill(null);
			screenStage.centerOnScreen();
			screenStage.setResizable(false);
		}
		
		FadeTransition ft = new FadeTransition(Duration.millis(500), screenStage.getScene().getRoot());
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(true);
		ft.play();
	
		screenStage.getScene().getRoot().setEffect(new Blend(BlendMode.COLOR_BURN));

		tipoOperacao = (String) objects[0];
		if (tipoOperacao.toLowerCase().equals("Alteracao".toLowerCase())) {
			agenda = (Agenda) objects[1];
			cliente = agenda.getCliente();
			dentista = agenda.getUsuarioDentista();
			txtCodigo.setText(String.valueOf(agenda.getCodigo()));
			txtCliente.setText(cliente.getNome());
			txtCliente.setEditable(false);
			LocalDate localDate = new java.sql.Date(agenda.getDataConsulta().getTime()).toLocalDate();
			dpData.setEditable(false);
			dpData.setValue(localDate);
			txtHora.setEditable(false);
			txtHora.setText(String.valueOf(agenda.getHoraConsulta().getTime() / (24 * 60 * 60 * 1000)));
		} else {
			if(gerenteLogin.getUsuario().getCargo().getDentista()) {
				txtDentista.setText(gerenteLogin.getUsuario().getLogin());
			}
			agenda = new Agenda();
		}
		
		screenStage.showAndWait();
		
	}
	
	protected void tabConfirmKeyEvent() {		
		txtCliente.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					if (!onClienteConfirm()) {
						event.consume();
					};
				}
			}
		});
		
		txtDentista.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					if (!onDentistaConfirm()) {
						event.consume();
					};
				}
			}
		});
		
		dpData.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
				//	if (!onPasswordConfirm()) {
					//	event.consume();
					//};
				}
			}
		});
	}
	
	public static Agenda execute(Stage stage, String operacao, Agenda agenda) {
		
		ViewFactory<TelaCadastroAgendamento> factoryView = new ViewFactory<TelaCadastroAgendamento>();

		TelaCadastroAgendamento agendamento = factoryView.getScreenControl(stage,
				TelaCadastroAgendamento.class.getResource(TELA_FXML), null, null);

		agendamento.stageOwner = stage;

		agendamento.show(operacao, agenda);
		
		return agenda;
	}

}
