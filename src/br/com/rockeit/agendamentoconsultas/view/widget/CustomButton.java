package br.com.rockeit.agendamentoconsultas.view.widget;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CustomButton extends Button {

	public CustomButton(String text) {
		super(text);
	}

	public CustomButton(String text, String image) {
		this(text);

		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(128);
		imageView.setFitWidth(128);
		imageView.setTranslateY(-20.00);

		setGraphic(imageView);
	}

	public CustomButton(String text, String image, double size) {
		this(text, image);
		setPrefHeight(size);
		setPrefWidth(size);
		setMaxHeight(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMinWidth(size);
		setContentDisplay(ContentDisplay.TOP);
		setStyle("-fx-font-weight:bold; -fx-font-size:20.0; -fx-padding: 15,15,15,15");
	}

	public CustomButton(String text, String image, double size, EventHandler<ActionEvent> event,
			boolean registerFocusEvent) {
		this(text, image, size);
		setOnAction(event);

		if (registerFocusEvent) {
			registerListenerFocus();
		}
	}

	private void registerListenerFocus() {
		focusedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean perdeuFoco, Boolean recebeuFoco) -> {

					if (recebeuFoco) {

						Region hb = (Region) getParent();

						VBox vb = (VBox) hb.getParent();

						double alturaAtualHBox = hb.getLayoutY() + hb.getHeight();

						double alturaTotalVBox = ((Region) vb.getParent().getParent()).getPrefHeight()
								+ Math.abs(vb.getLayoutY());

						double alturaRealHb = hb.getHeight() + hb.getPadding().getTop() + hb.getPadding().getBottom()
								+ vb.getSpacing();

						if (alturaAtualHBox >= alturaTotalVBox) {

							vb.setLayoutY(vb.getLayoutY() - alturaRealHb);

						} else if (vb.getLayoutY() < 0 && alturaAtualHBox > vb.getLayoutY()) {

							vb.setLayoutY(vb.getLayoutY() + alturaRealHb);
						}
					}
				});
	}
}
