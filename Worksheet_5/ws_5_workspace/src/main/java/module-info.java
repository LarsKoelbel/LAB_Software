module com.example.ws_5_workspace {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ws_5_workspace to javafx.fxml;
    exports com.example.ws_5_workspace;
}