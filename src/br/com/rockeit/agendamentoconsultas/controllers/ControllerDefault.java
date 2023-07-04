package br.com.rockeit.agendamentoconsultas.controllers;

import br.com.rockeit.agendamentoconsultas.application.ApplicationFX;
import br.com.rockeit.agendamentoconsultas.interfaces.IControllerFXML;
import br.com.rockeit.agendamentoconsultas.negocio.GerenteLogin;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public abstract class ControllerDefault implements Initializable, IControllerFXML {
	
	protected Stage screenStage = null;
	
	protected GerenteLogin gerenteLogin = GerenteLogin.getInstance();
	
	public ControllerDefault() {
		
	}

	public Stage getScreenStage() {
		return screenStage;
	}
	
	public void setScreenStage(Stage stage) {
		this.screenStage = stage;
		screenStage.getIcons().add(ApplicationFX.IMAGEM_LOGO);		
	}
	
	protected void close(boolean confirmation) {
		if (confirmation) {
			if(AlertFactory.showCustomAlert("Sair", "Deseja realmente fechar?", AlertType.CONFIRMATION)) {
				closeStage();
			}
		} else {
			closeStage();
		}
		
	}
	
	protected void closeStage() {
		screenStage.close();
		
		onStageClose();
	}
	
	protected void onStageClose() {
		
	}
	
	protected void registerKeyEvents(){
		closeScreenKeyEvent(true);	
		fullScreenKeyEvent();
	}
	
	protected void showErrorAlert(String message) {
		AlertFactory.showErrorAlert(message);
	}
	
	protected void showConfirmationAlert(String message) {
		AlertFactory.showConfirmationAlert(message);
	}
	
	protected void showAlert(String header, String message) {
		AlertFactory.showAlert(header, message);
	}
	
	protected boolean showYesOrNoAlert(String message) {
		return AlertFactory.showYesOrNoAlert(message);
	}
	
	protected void closeScreenKeyEvent(boolean confirm) {
		closeScreenKeyEvent(screenStage, confirm);
	}
	
	protected void closeScreenKeyEvent(Stage stage, boolean confirm) {
		stage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					close(confirm);
				}
			}
		});
	}
	
	protected void fullScreenKeyEvent() {
		screenStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
            	screenStage.setFullScreen(!screenStage.isFullScreen());
            }
        });
	}
	
	@Override
	public void setStage(Stage stage) {
		screenStage = stage;
	}
	
	@Override
	public void close() {
		closeStage();
	}

}
