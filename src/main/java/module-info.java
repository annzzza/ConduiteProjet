module com.example.ConduiteProjet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ConduiteProjet to javafx.fxml;
    exports com.example.ConduiteProjet;
}