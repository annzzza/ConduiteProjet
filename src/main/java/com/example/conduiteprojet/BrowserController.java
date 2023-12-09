package com.example.conduiteprojet;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrowserController {

    @FXML
    public ListView<String> listAssistanceView;
    public Label titleLabel;

    private AssistanceDaoImplementation a_daoimpl = new AssistanceDaoImplementation();
    public List<Assistance> getCorrespondingList() throws SQLException {
        String role = PreferencesManager.getRole();

        if (role.equals("Benevole")) {
            titleLabel.setText("List of Requests");
            return a_daoimpl.getAssistanceRequests();
        } else {
            titleLabel.setText("List of Offers");
            return a_daoimpl.getAssistanceOffers();
        }
    }

    public void initialize() throws SQLException {
        List<Assistance> listAss = getCorrespondingList();
        List<String> listStringAss = new ArrayList<String>();

        for(final Assistance ass : listAss){
            listStringAss.add("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n ");
        }

        ObservableList<String> observableListAssistance = FXCollections.observableArrayList(listStringAss);
        listAssistanceView.setItems(observableListAssistance);
    }



    public void onAddAssistanceButtonClick() throws Exception {
        CreateAssistanceLoader loader = new CreateAssistanceLoader();
        loader.start(new Stage());
        initialize();
    }
}
