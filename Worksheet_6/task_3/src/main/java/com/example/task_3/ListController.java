package com.example.task_3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ListController {


    public TextField input;
    public ListView<String> list;

    public void add(MouseEvent mouseEvent) {
        String content = input.getText();
        if (content == null || content.isBlank())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText("Please enter valid input");
            alert.showAndWait();
        }
        list.getItems().add(content);
        input.clear();
    }

    public void reset(MouseEvent mouseEvent) {
        list.getItems().clear();
    }
}
