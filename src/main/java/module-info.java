module com.example.conduiteprojet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.conduiteprojet to javafx.fxml;
    exports com.example.conduiteprojet;
}