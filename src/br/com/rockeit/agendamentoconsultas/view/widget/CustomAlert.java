package br.com.rockeit.agendamentoconsultas.view.widget;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomAlert extends Alert {
	
	public static final Image LOGO =  new Image("/br/com/rockeit/agendamentoconsultas/images/logo.png");
	public CustomAlert(AlertType alertType) {
		super(alertType);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(LOGO);
		stage.setAlwaysOnTop(true);
		initModality(Modality.WINDOW_MODAL);
	}
}
