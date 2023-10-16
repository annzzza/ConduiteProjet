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
import java.util.List;


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
        AssistanceDaoImplementation assistanceDaoImpl = new AssistanceDaoImplementation();

        try {
            String title = titleField.getText();
            Assistance ass = new Assistance();
            ass.setTitle(title);
            assistanceDaoImpl.add(ass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Assistance> listAssistances = assistanceDaoImpl.getAssistances();
            for(Assistance ass: listAssistances) {
                System.out.println(ass.getId() + " " + ass.getTitle());
            }
        } catch (SQLException e) {
            System.out.println("SQLException getAllAssistances." + e);
        }



    }
}
