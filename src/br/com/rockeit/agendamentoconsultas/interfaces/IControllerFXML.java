package br.com.rockeit.agendamentoconsultas.interfaces;

import javafx.stage.Stage;

public interface IControllerFXML {
	public void setStage(Stage stage);
	
	public void loadParams(Object... objects);

	public void show(Object... objects);

	public void close();
}
