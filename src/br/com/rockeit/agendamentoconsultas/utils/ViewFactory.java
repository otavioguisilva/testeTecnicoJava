package br.com.rockeit.agendamentoconsultas.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import br.com.rockeit.agendamentoconsultas.application.ApplicationFX;
import br.com.rockeit.agendamentoconsultas.interfaces.IControllerFXML;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.MotionBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ViewFactory<T extends IControllerFXML> {
	public T changeScreen(URL screen, Object... objects) 
	throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return changeScreen(screen, ApplicationFX.getStage(), objects);
	}
	
	public T changeScreen(URL screen, Stage primaryStage, Object... objects) 
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return changeScreen(screen, primaryStage, null, true, objects);
	}
	
	public T changeScreen(URL screen, Stage ownerStage, Stage screenStage, boolean show, Object[] objects)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		FXMLLoader loader = new FXMLLoader(screen);

		Parent page = loader.load();

		Stage stage = screenStage == null ? new Stage() : screenStage;

		if (ownerStage != null) {
			stage.initOwner(ownerStage);
			stage.initModality(Modality.WINDOW_MODAL);
		}

		Scene scene = new Scene(page);

		stage.setScene(scene);

		stage.centerOnScreen();

		loadParams(loader, stage, objects);

		T controller = loader.getController();

		if (show) {
			setStageEffect(stage);
			controller.show();
		}

		return controller;
	}
	
	public static void setStageEffect(Stage stage) {

		if (stage == null) {
			return;
		}
		
		FadeTransition ft = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(true);
		ft.play();

		if (stage.getOwner() != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {

					stage.getOwner().getScene().getRoot().setEffect(new MotionBlur(0.0, 5.0));
				}
			});
		}
	}
	
	protected void loadParams(FXMLLoader loader, Stage stageTela, Object... object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		T t = loader.getController();

		t.setStage(stageTela);

		if (object != null) {
			t.loadParams(object);
		}
	}
	
	public T getScreenControl(Stage ownerStage, URL tela, Object... object) {

		try {
			return changeScreen(tela, ownerStage, null, false, object);
		} catch (Exception e) {
			AlertFactory.showException(e);
		}
		return null;
	}
	
}
