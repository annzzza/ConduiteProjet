package com.example.conduiteprojet.app;

import com.example.conduiteprojet.auth.User;
import com.example.conduiteprojet.database.AssistanceDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    @FXML
    public ListView<String> listAssistanceView;
    public Label titleLabel;

    private AssistanceDaoImplementation a_daoimpl = new AssistanceDaoImplementation();
    String role = PreferencesManager.getRole();
    public List<Assistance> getCorrespondingList() throws SQLException {

        if (role.equals("Benevole")) {
            titleLabel.setText("List of Requests");
            return a_daoimpl.getAssistanceRequests();
        } else if ( role.equals("Valideur")) {
            titleLabel.setText("List of Pending Requests");
            return  a_daoimpl.getAssistanceToValidate();
        } else {
            titleLabel.setText("List of Offers");
            return a_daoimpl.getAssistanceOffers();
        }
    }

    public void initialize() throws SQLException {
        List<Assistance> listAss = getCorrespondingList();
        List<String> listStringAss = new ArrayList<String>();

        if (role.equals("BENEVOLE")){
            for(final Assistance ass : listAss) {
                listStringAss.add("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n ");

                System.out.println(ass.getCreatorId());
            }
        } else if (role.equals("PATIENT")) {
            for(final Assistance ass : listAss) {
                listStringAss.add("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n "+ "Status: " + ass.getStatus() + "\n ");
            }
        }


        System.out.println(role);

        ObservableList<String> observableListAssistance = FXCollections.observableArrayList(listStringAss);
        listAssistanceView.setItems(observableListAssistance);
    }



    public void onAddAssistanceButtonClick() throws Exception {
        CreateAssistanceLoader loader = new CreateAssistanceLoader();
        loader.start(new Stage());
        initialize();
    }

    public void onRefreshButtonClick(){
        //todo
        //listAssistanceView.refresh();
    }
}
