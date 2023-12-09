module com.example.conduiteprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires org.apache.logging.log4j;


//    opens com.example.conduiteprojet to javafx.fxml;
//    exports com.example.conduiteprojet;
    exports com.example.conduiteprojet.database;
    opens com.example.conduiteprojet.database to javafx.fxml;
    exports com.example.conduiteprojet.auth;
    opens com.example.conduiteprojet.auth to javafx.fxml;
    exports com.example.conduiteprojet.utils;
    opens com.example.conduiteprojet.utils to javafx.fxml;
    exports com.example.conduiteprojet.app;
    opens com.example.conduiteprojet.app to javafx.fxml;
}