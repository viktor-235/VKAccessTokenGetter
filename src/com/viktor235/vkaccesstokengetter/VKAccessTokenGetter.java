package com.viktor235.vkaccesstokengetter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Виктор
 */
public class VKAccessTokenGetter extends Application {

	private final static String OUTPUT_FILE_NAME = "at";
	private static Handler handler;

	@Override
	public void start(Stage primaryStage) {
		String url = handler.getConnectionUrl();

		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(url);

		StackPane root = new StackPane();
		root.getChildren().add(browser);

		Scene scene = new Scene(root, 650, 500);

		primaryStage.setTitle("Acces Token Getter");
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					String urlWithAT = webEngine.getLocation();
					handler.setInputUrl(urlWithAT);
					handler.parseUrl();
					handler.writeAccesTokenToFile(OUTPUT_FILE_NAME);
					System.out.print("See file: " + OUTPUT_FILE_NAME);
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		});
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.print("Usage:\nNeed set URL for getting access token");
			return;
		}
		handler = new Handler();
		handler.setConnectionUrl(args[0]);
		launch(args);
	}

}
