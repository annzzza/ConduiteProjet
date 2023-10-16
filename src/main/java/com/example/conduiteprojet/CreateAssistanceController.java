package com.example.conduiteprojet;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;


public class CreateAssistanceController{

    public TextField titleField;
    public TextField descriptionField;
    public Button createButton;
    @FXML
    private Button cancelButton;

    private Connection con;

    @FXML
    protected void onCancelButtonClick() {
        System.out.println("cancelButtonClicked");
    }

    public void onCreateButtonClick(ActionEvent actionEvent) {
        System.out.println("createButtonClicked");
        try {
            con = Database.getDBConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
