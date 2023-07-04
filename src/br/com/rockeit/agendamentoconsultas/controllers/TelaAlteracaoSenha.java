package br.com.rockeit.agendamentoconsultas.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.utils.Encrypt;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class TelaAlteracaoSenha extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaAlteracaoSenha.fxml";
	
	@FXML
	private TextField txtSenhaAntiga;

	@FXML
	private TextField txtSenhaNova;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnCancel;
	
	private static  Usuario usuarioLogado;

	private Stage stageOwner = null;
	
	@FXML
	public void onBtnConfirmAction() {
		if ((!usuarioLogado.getAlteraSenhaLogin() &&  (txtSenhaAntiga.getText() == null || txtSenhaAntiga.getText().length() == 0)) ||
				txtSenhaNova.getText() == null || txtSenhaNova.getText().length() == 0) {
			showAlert("Dados Invalidos", "Atenção os campos não podem estar vazios!");
		} else {
			String novaSenha = txtSenhaNova.getText();
			
			try {
				novaSenha = Encrypt.encrypt86(novaSenha, 33);
				usuarioLogado.setSenha(novaSenha);
				usuarioLogado.setAlteraSenhaLogin(false);
				boolean alterou = Usuario.alteraSenha(usuarioLogado);
				
				if (alterou) {
					showConfirmationAlert("Senha alterada com sucesso");
					close(false);
				} else {
					showErrorAlert("Erro ao alterar a senha, tente novamente");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void onBtnCancelAction() {
		close(true);
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
		registerKeyEvents();
		screenStage.setAlwaysOnTop(true);
		if (screenStage.getStyle() != StageStyle.TRANSPARENT) {
			screenStage.initStyle(StageStyle.TRANSPARENT);
			screenStage.initModality(Modality.WINDOW_MODAL);
			screenStage.setHeight(((Stage) screenStage.getOwner()).getHeight());
			screenStage.setWidth(((Stage) screenStage.getOwner()).getWidth());
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
		
		usuarioLogado = (Usuario) objects[0];
		
		if(usuarioLogado.getAlteraSenhaLogin()) {
			txtSenhaAntiga.setDisable(true);
		}
		
		screenStage.showAndWait();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public static Usuario execute(Stage stage, Usuario usuario) {

		ViewFactory<TelaAlteracaoSenha> factoryView = new ViewFactory<TelaAlteracaoSenha>();

		TelaAlteracaoSenha alteraSenha = factoryView.getScreenControl(stage,
				TelaAlteracaoSenha.class.getResource(TELA_FXML), null, null);

		alteraSenha.stageOwner = stage;

		alteraSenha.show(usuario);
		
		return usuarioLogado;
	}

}
