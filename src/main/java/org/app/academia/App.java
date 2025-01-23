package org.app.academia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        var fxmlPath = App.class.getResource("/org/app/academia/fxml/TelaMain.fxml");
        System.out.println("FXML Path: " + fxmlPath);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/org/app/academia/fxml/TelaMain.fxml"));
        primaryStage.setTitle("Academia");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println(App.class.getResource("/org/app/academia/fxml/TelaMain.fxml"));
        launch(args);
    }
}