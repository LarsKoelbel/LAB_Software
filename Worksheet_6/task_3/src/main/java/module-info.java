module com.example.task_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task_3 to javafx.fxml;
    exports com.example.task_3;
}