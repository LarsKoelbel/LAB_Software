package com.example.task_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WikibooksApplication extends Application {
    private static final float SCALE = 1;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WikibooksApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920 * SCALE, 1080 * SCALE);
        stage.setTitle("Itsy bitsy tini wini WikiBooks search machini");

        // Set icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/com/example/task_4/media/images/icon.png"
        )));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

}
