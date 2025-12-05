package com.example.task_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SampleController {
    public Label helloWorld;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    }
}
