package com.example.task_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WikibooksApplication extends Application {
    private static final float SCALE = 1;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WikibooksApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), (float)1920 * SCALE, (float)1080 * SCALE);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
