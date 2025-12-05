module com.example.task_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.management;
    requires org.json;


    opens com.example.task_4 to javafx.fxml;
    exports com.example.task_4;
}