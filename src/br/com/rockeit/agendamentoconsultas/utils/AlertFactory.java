package br.com.rockeit.agendamentoconsultas.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import br.com.rockeit.agendamentoconsultas.view.widget.CustomAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class AlertFactory {
	private AlertFactory() {
		super();
	}
	
	public static String showOptionConfirmationAlert(String content, String... buttons) {
		return showOptionConfirmationAlert(content, AlertType.CONFIRMATION, buttons);
	}
	
	public static String showOptionConfirmationAlert(String content, AlertType alertType, String... btns) {

		CustomAlert alert = new CustomAlert(AlertType.CONFIRMATION);

		alert.getButtonTypes().remove(ButtonType.OK);
		alert.getButtonTypes().remove(ButtonType.CANCEL);

		ButtonType[] buttonType = new ButtonType[btns.length];

		for (int indice = 0; indice < btns.length; indice++) {
			buttonType[indice] = new ButtonType(btns[indice]);
			alert.getButtonTypes().add(indice, buttonType[indice]);
		}

		alert.setContentText(content);

		alert.setHeaderText(null);

		Optional<ButtonType> result = alert.showAndWait();

		return result.get().getText();
	}
	
	public static boolean showAlert(String header, String content) {
		CustomAlert alert = new CustomAlert(AlertType.INFORMATION);
		
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		GridPane messageGrid = new GridPane();
		messageGrid.setMaxWidth(Double.MAX_VALUE);
		messageGrid.setStyle("-fx-background-color:white; -fx-padding:5px");
		
		return alertReturn(alert);
	}

	public static boolean showCustomAlert(String header, String content, AlertType alertType) {
		CustomAlert alert = new CustomAlert(alertType);
		
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		GridPane messageGrid = new GridPane();
		messageGrid.setMaxWidth(Double.MAX_VALUE);
		messageGrid.setStyle("-fx-background-color:white; -fx-padding:5px");
		
		return alertReturn(alert);
	}
	public static void showException(Throwable e) {
		CustomAlert alert = new CustomAlert(AlertType.ERROR);

		alert.setHeaderText(e.getMessage());

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Erro completo:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
	
	public static boolean showYesOrNoAlert(String content) {
		String result = showOptionConfirmationAlert(content, "Sim", "Não");
		
		return result.equalsIgnoreCase("Sim");
	}
	
	public static boolean showConfirmationAlert(String content) {
		
		CustomAlert alert = new CustomAlert(AlertType.CONFIRMATION);
		alert.setContentText(content);
		alert.setHeaderText("Confirma?");
		
		return alertReturn(alert);
		
	}
	
	public static boolean showErrorAlert(String content) {
		CustomAlert alert = new CustomAlert(AlertType.ERROR);
		alert.setContentText(content);
		alert.setHeaderText("Erro!");
		
		return alertReturn(alert);
	}
	
	private static boolean alertReturn(Alert alert) {
		
		Optional<ButtonType> result = alert.showAndWait();
		
		return result.get() == ButtonType.OK;
	}
	
}
