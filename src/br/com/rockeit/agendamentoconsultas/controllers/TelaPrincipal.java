package br.com.rockeit.agendamentoconsultas.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.com.rockeit.agendamentoconsultas.application.AgendamentoConsultas;
import br.com.rockeit.agendamentoconsultas.dominio.Agenda;
import br.com.rockeit.agendamentoconsultas.dominio.Cargo;
import br.com.rockeit.agendamentoconsultas.dominio.Cliente;
import br.com.rockeit.agendamentoconsultas.dominio.Tela;
import br.com.rockeit.agendamentoconsultas.dominio.Usuario;
import br.com.rockeit.agendamentoconsultas.dominio.permissoes.PermissoesUsuario;
import br.com.rockeit.agendamentoconsultas.utils.AlertFactory;
import br.com.rockeit.agendamentoconsultas.view.widget.CustomButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class TelaPrincipal<T extends Cloneable> extends ControllerDefault {
	
	public final static String TELA_FXML = "/br/com/rockeit/agendamentoconsultas/view/fx/TelaPrincipal.fxml";
	
	@FXML
	private Label lblLoginUsuario;
	
	@FXML
	private Label lblNomeUsuario;
	
	@FXML 
	private Label lblCargo;
	
	@FXML
	private TableView<Agenda> tbvAgendaRapida;
	
	@FXML
	private BorderPane bdpMenu;
	
	@FXML
	protected Label lblDataHora;

	private SimpleStringProperty dataHoraProperty;
	
	private List<CustomButton> botoes = new ArrayList<CustomButton>();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dataHoraProperty = new SimpleStringProperty();
		lblDataHora.textProperty().bind(dataHoraProperty);
		popularCampoDataHora();
	}
	
	@Override
	protected void onStageClose() {
		super.onStageClose();

		Window owner = screenStage.getOwner();

		if (owner instanceof Stage) {
			((Stage) owner).getScene().getRoot().setEffect(null);
		}
		
		AgendamentoConsultas.close();
	}
	
	@Override
	public void close() {
		
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
		Usuario usuarioLogado = gerenteLogin.getUsuario();
		Cargo cargo = usuarioLogado.getCargo();
		
		lblLoginUsuario.setText(usuarioLogado.getLogin());
		lblNomeUsuario.setText(usuarioLogado.getNome() + " " + usuarioLogado.getSobreNome());
		lblCargo.setText(cargo.getCodigo() + " - " + cargo.getNome());
		
		if (!cargo.getDentista()) {
			tbvAgendaRapida.setMaxSize(0, 0);
			tbvAgendaRapida.setVisible(false);
		}
		
		configurarPainelBotoes(300.0, 20.0, 5);
		
		screenStage.setAlwaysOnTop(true);
		screenStage.show();
	}
	
	private void popularCampoDataHora() {

		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(() -> {
			Platform.runLater(() -> {
				Date data = new Date();
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String dataFormatada = format.format(data);
				dataHoraProperty.set(dataFormatada);
			});
		}, 0, 30, TimeUnit.SECONDS);
	}
	
	private void configurarPainelBotoes(double tamanhoBotoes, double espacoEntreBotoes, int botoesPorLinha) {

		VBox vBox = new VBox();

		vBox.setSpacing(espacoEntreBotoes);

		vBox.alignmentProperty().set(Pos.CENTER);

		HBox hBoxGeral = new HBox(new AnchorPane(vBox));
		hBoxGeral.alignmentProperty().set(Pos.CENTER);

		hBoxGeral.setPrefHeight(660);

		bdpMenu.setCenter(hBoxGeral);

		popularListaBotoes(tamanhoBotoes);

		configurarLinhaBotoes(vBox, espacoEntreBotoes, botoesPorLinha);
	}
	
	private void popularListaBotoes(double tamanhoBotao) {
		List<PermissoesUsuario> permissoesTela = PermissoesUsuario.getPermissoesConsultaUsuario(gerenteLogin.getUsuario().getLogin());
		
		for (PermissoesUsuario permissao : permissoesTela) {
			botoes.add(new CustomButton(permissao.getTela().getDescricao(),
					getClass().getResource("/br/com/rockeit/agendamentoconsultas/images/" + permissao.getTela().getNomeImagem()).toString(), 
					tamanhoBotao, registrarEventoBotao(permissao.getTela(), "br.com.rockeit.agendamentoconsultas.dominio." + permissao.getTela().getNomeClasse()), false));
		}
	}
	
	private void configurarLinhaBotoes(VBox vBox, double espacoEntreBotoes, int botoesPorLinha) {

		HBox hBox = new HBox();

		for (int i = 0; i < botoes.size(); i++) {

			if (i % botoesPorLinha == 0) {
				hBox = new HBox();
				hBox.setSpacing(espacoEntreBotoes);
				hBox.alignmentProperty().set(Pos.CENTER);
				vBox.getChildren().add(hBox);
			}

			hBox.getChildren().add(botoes.get(i));
		}
	}
	
	private EventHandler<ActionEvent> registrarEventoBotao(Tela tela, String nomeClasse) { 
		EventHandler<ActionEvent> evento = null;
		try {
			evento = new EventHandler<ActionEvent>() {

				Class<?> classe = Class.forName(nomeClasse);

				@Override
				public void handle(ActionEvent event) {
					try {
						Platform.runLater(()->{
							TelaPesquisaGenerica.execute(screenStage, tela, classe);
						});
					} catch (Exception e) {
						AlertFactory.showException(e);
					}

				}
			};
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return evento;
	}
}
