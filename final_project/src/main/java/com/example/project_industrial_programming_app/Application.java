package com.example.project_industrial_programming_app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);



        Image icon = new Image(getClass().getResourceAsStream("/com/example/project_industrial_programming_app/your-icon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Arifmetic_finder-helper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
