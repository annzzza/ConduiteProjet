package com.example.conduiteprojet.app;

import com.example.conduiteprojet.app.Assistance;
import com.example.conduiteprojet.database.AssistanceDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;


public class CreateAssistanceController{

    public TextField titleField;
    public TextField descriptionField;
    public Button createButton;
    public DatePicker dueDatePicker;
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
            ass.setCreatorId(1); // @TODO change to current user when connexion will be set up
            ass.setDescription(descriptionField.getText());
            ass.setStatus(Assistance.Status.OPEN);
            ass.setCancelled(false);
            if (PreferencesManager.getRole().equals("Benevole")) {
                ass.setType(Assistance.Type.OFFER);
            } else {
                ass.setType(Assistance.Type.REQUEST);
            }
            java.util.Date date = java.util.Date.from(dueDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            ass.setDueDate(new java.sql.Date(date.getTime()));
            ass.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
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
