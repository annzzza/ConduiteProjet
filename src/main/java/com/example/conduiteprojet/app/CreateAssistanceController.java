package com.example.conduiteprojet.app;

import com.example.conduiteprojet.database.AssistanceDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;


public class CreateAssistanceController{

    private static final Logger LOGGER = LogManager.getLogger(CreateAssistanceController.class);


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
            ass.setCreatorId(PreferencesManager.getUserID());
            ass.setDescription(descriptionField.getText());
            ass.setCancelled(false);
            if (PreferencesManager.getRole().equals("BENEVOLE")) {
                ass.setStatus(Assistance.Status.ACCEPTED);
                ass.setType(Assistance.Type.OFFER);
            } else {
                ass.setStatus(Assistance.Status.PENDING);
                ass.setType(Assistance.Type.REQUEST);
            }
            java.util.Date date = java.util.Date.from(dueDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            ass.setDueDate(new java.sql.Date(date.getTime()));
            ass.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
            assistanceDaoImpl.add(ass);

            LOGGER.info("Created Assistance with Creator ID = " + ass.getCreatorId());

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
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
