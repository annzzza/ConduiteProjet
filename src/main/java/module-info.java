module com.example.conduiteprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;


    opens com.example.conduiteprojet to javafx.fxml;
    exports com.example.conduiteprojet;
}