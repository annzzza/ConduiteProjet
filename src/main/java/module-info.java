module com.example.oups {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oups to javafx.fxml;
    exports com.example.oups;
}