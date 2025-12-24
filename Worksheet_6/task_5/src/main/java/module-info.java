module com.example.task_5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task_5 to javafx.fxml;
    exports com.example.task_5;
}