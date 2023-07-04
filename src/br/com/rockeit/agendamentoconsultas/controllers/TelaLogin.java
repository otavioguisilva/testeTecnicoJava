package br.com.rockeit.agendamentoconsultas.controllers;

import java.awt.event.FocusEvent;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.application.AgendamentoConsultas;
import br.com.rockeit.agendamentoconsultas.application.ApplicationFX;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import br.com.rockeit.agendamentoconsultas.utils.Encrypt;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class TelaLogin extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaLogin.fxml";
	
	@FXML
	private TextField txtUserLogin;

	@FXML
	private TextField txtPassword;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnCancel;
	
	private Usuario usuarioLogar;
	
	private boolean logou;
	
	@FXML
	public boolean onUserConfirm() {
		
		boolean result = false;
		
		if (txtUserLogin.getText() == null || txtUserLogin.getText().trim().length() == 0) {
			showAlert("Dados Invalidos", "Atenção o campo de usuário não pode estar vazio!");
		} else {
			String loginUsuario = txtUserLogin.getText();
			
			usuarioLogar = Usuario.logar(loginUsuario);
			
			if (usuarioLogar != null) {
				if(usuarioLogar.getAlteraSenhaLogin()) {			
					usuarioLogar = TelaAlteracaoSenha.execute(screenStage, usuarioLogar);
				}
				
				result = true;
				
				txtPassword.requestFocus();
			} else {
				txtUserLogin.setText("");
			}		
		}
		
		return result;
		
	}
	
	@FXML
	public boolean onPasswordConfirm() {
		boolean result = false; 
		
		if (txtPassword.getText() == null || txtPassword.getText().trim().length() == 0) {
			showAlert("Dados Invalidos", "Atenção o campo de senha não pode estar vazio!");
		} else {
			String senhaUsuario = txtPassword.getText();
			
			try {
				senhaUsuario = Encrypt.encrypt86(senhaUsuario, 33);
				
				if (senhaUsuario.equals(usuarioLogar.getSenha())) {
					logou = true;
					
					result = true;
					
					btnConfirm.requestFocus();
				} else {
					showErrorAlert("Senha inválida");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	@FXML
	public void onBtnConfirmAction() {
		if (txtUserLogin.getText() == null || txtUserLogin.getText().trim().length() == 0 ||
				txtPassword.getText() == null || txtPassword.getText().trim().length() == 0) {
			showAlert("Dados Invalidos", "Atenção os campos usuário e senha não podem estar vazios!");
		} else {
			if (logou) {
				gerenteLogin.setUsuario(usuarioLogar);
				
				abrirTelaPrincipal();

				close();
			} else {
				logou = onPasswordConfirm();
				
				if (logou) {
					gerenteLogin.setUsuario(usuarioLogar);
					
					abrirTelaPrincipal();

					close();
				}
			}
		}
	}
	
	@FXML
	public void onBtnCancelAction() {
		close(true);
	}
	
	protected void enterConfirmButtonEvent() {
		btnConfirm.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					onBtnConfirmAction();
				}
			}
		});
		
		btnCancel.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					onBtnCancelAction();
				}
			}
		});
	}
	
	protected void tabConfirmKeyEvent() {
		txtUserLogin.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					if (!onUserConfirm()) {
						event.consume();
					};
				}
			}
		});
		
		txtPassword.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					if (!onPasswordConfirm()) {
						event.consume();
					};
				}
			}
		});
	}

	@Override
	public void setStage(Stage stage) {
		super.setScreenStage(stage);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.initModality(Modality.WINDOW_MODAL);	
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
	public void loadParams(Object... objects) {
		if (objects == null || objects.length == 0) {

			close();
			return;
		}		
	}

	@Override
	public void show(Object... objects) {
		closeScreenKeyEvent(true);
		tabConfirmKeyEvent();
		screenStage.setAlwaysOnTop(true);
		screenStage.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private void abrirTelaPrincipal() {
		try {

			ViewFactory<TelaPrincipal> viewFactory = new ViewFactory<TelaPrincipal>();

			TelaPrincipal telaPrincipal = viewFactory.changeScreen(AgendamentoConsultas.class.getResource(TelaPrincipal.TELA_FXML), null,
					ApplicationFX.getStage(), false, null);

			telaPrincipal.show();

		} catch (Exception e) {
			e.printStackTrace();
			AlertFactory.showException(e);
		}
	}

}
