package br.com.rockeit.agendamentoconsultas.application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;

import br.com.rockeit.agendamentoconsultas.controllers.TelaLogin;
import br.com.rockeit.agendamentoconsultas.dominio.Cargo;
import br.com.rockeit.agendamentoconsultas.dominio.Tela;
import br.com.rockeit.agendamentoconsultas.dominio.permissoes.Permissoes;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import br.com.rockeit.agendamentoconsultas.system.SystemFile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ApplicationFX extends Application {	
	
	private static Stage stage;
	
	public static final Image IMAGEM_LOGO = new Image("/br/com/rockeit/agendamentoconsultas/images/logo.png");
	
	public static final java.awt.Image IMAGEM_LOGO_AWT = new ImageIcon(
			ApplicationFX.class.getResource("/br/com/rockeit/agendamentoconsultas/images/logo.png")).getImage();

	public static String getPathAplicacao() {
		return System.getProperty("user.dir");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			stage = primaryStage;
			
			stage.initStyle(StageStyle.TRANSPARENT);
			
			ViewFactory<TelaLogin> viewFactory = new ViewFactory<TelaLogin>();
			
			configureStage();
			
			Object[] params = new Object[1];
			
			params[0] = "";
			 
			TelaLogin loginScreen = viewFactory.changeScreen(AgendamentoConsultas.class.getResource(TelaLogin.TELA_FXML), null, new Stage(), false, params);	
			
			loginScreen.show();
			
		}
		catch(Exception e) {
			AlertFactory.showException(e);
		}
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void configureStage() {
		
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(
				new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_ANY, KeyCombination.SHIFT_ANY));

		stage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.isControlDown() && event.isShiftDown() && KeyCode.UP.equals(event.getCode())) {
					setFullScreenStage();
				}
			}
		});

		setFullScreenStage();

		stage.centerOnScreen();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);

			}
		});
	}
	
	private static void setFullScreenStage() {
		stage.setAlwaysOnTop(true);
		stage.setFullScreen(true);
		stage.setMaximized(true);
	}
	
}
