package gnd_control.guiview;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MyGoogleMap extends Application {

	WebEngine engine;
	WebView webView;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			MyGoogleMap.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//engine = new WebEngine(getClass().getResource("/html/myMap.html").toString());
		webView = new WebView();
		engine = webView.getEngine();
		URL googleURL = getClass().getResource("/html/myMap.html");
		engine.load(googleURL.toExternalForm());
		//engine.load(getClass().getResource("/html/myMap.html").toString());
		primaryStage.setScene(new Scene(webView, 800, 800, Color.web("#666970")));
		primaryStage.show();
		
	}

}
