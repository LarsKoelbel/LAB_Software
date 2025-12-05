module com.example.task_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task_2 to javafx.fxml;
    exports com.example.task_2;
}