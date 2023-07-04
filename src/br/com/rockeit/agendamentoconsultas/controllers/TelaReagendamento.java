package br.com.rockeit.agendamentoconsultas.controllers;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.rockeit.agendamentoconsultas.dominio.Agenda;
import br.com.rockeit.agendamentoconsultas.utils.ViewFactory;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class TelaReagendamento extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaReagendamento.fxml";
	
	@FXML
	private DatePicker dpNovaData;

	@FXML
	private TextField txtNovaHora;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnCancel;
	
	private Agenda agenda;

	private Stage stageOwner = null;
	
	@FXML
	public void onBtnConfirmAction() {
		if (txtNovaHora.getText() == null || txtNovaHora.getText().length() == 0 ||
				dpNovaData.getValue() == null) {
			showAlert("Dados Invalidos", "Atenção os campos não podem estar vazios!");
		} else {
			Date novaData = Date.from(dpNovaData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			String novaHora = txtNovaHora.getText();
			
			try {
				
				Time hora = Time.valueOf(novaHora);
				agenda.setDataConsulta(novaData);
				agenda.setHoraConsulta(hora);
				Agenda.alteraRegistro(agenda);
				
				showAlert("Reagendado", "Reagendado com sucesso");
				
				close(false);


			} catch (Exception e) {
				e.printStackTrace();
				showErrorAlert("Erro ao Reagendar a data, tente novamente");
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
		agenda = (Agenda) objects[0];
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

		screenStage.showAndWait();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public static Agenda execute(Stage stage, Agenda agenda) {
		
		ViewFactory<TelaReagendamento> factoryView = new ViewFactory<TelaReagendamento>();

		TelaReagendamento reagendamento = factoryView.getScreenControl(stage,
				TelaReagendamento.class.getResource(TELA_FXML), null, null);

		reagendamento.stageOwner = stage;

		reagendamento.show(agenda);
		
		return agenda;
	}

}
