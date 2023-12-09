package com.example.conduiteprojet.app;

import com.example.conduiteprojet.auth.LogInController;
import com.example.conduiteprojet.auth.User;
import com.example.conduiteprojet.database.AssistanceDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    private static final Logger LOGGER = LogManager.getLogger(MainWindowController.class);

    @FXML
    public ListView<HBox> listAssistanceView;
    public TabPane tabPane;
    public Tab titleLabel;
    public VBox rightVBox;

    private AssistanceDaoImplementation a_daoimpl = new AssistanceDaoImplementation();
    String role = PreferencesManager.getRole();
    public List<Assistance> getCorrespondingList() throws SQLException {
        if (role.equals("BENEVOLE")) {
            return a_daoimpl.getAssistanceRequests();
        } else if ( role.equals("VALIDEUR")) {
            return  a_daoimpl.getAssistanceToValidate();
        } else {
            return a_daoimpl.getAssistanceOffers();
        }
    }

    public void initialize() throws SQLException {
        List<Assistance> listAss = getCorrespondingList();
        List<HBox> listStringAss = new ArrayList<HBox>();

        LOGGER.info("Initialization of Main Window has started.");

        if (role.equals("BENEVOLE")){
            titleLabel.setText("List of Requests");
            for(final Assistance ass : listAss) {
                HBox hBox = new HBox();
                Button answerButton = new Button("Accept");
                answerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        LOGGER.info("Accept request button has been clicked.");

                        Label rightLabel = new Label("\n" + ass.getTitle() + ": Do you accept the request?\n");
                        Button confirmButton = new Button("Confirm");
                        rightVBox.getChildren().add(rightLabel);
                        rightVBox.getChildren().add(confirmButton);
                    }
                });
                hBox.getChildren().add(answerButton);
                hBox.getChildren().add(new Label("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n "));
                listStringAss.add(hBox);

                System.out.println(ass.getCreatorId());
            }
        } else if (role.equals("PATIENT")) {
            titleLabel.setText("List of Offers");

            for(final Assistance ass : listAss) {
                HBox hBox = new HBox();
                Button answerButton = new Button("Take");
                answerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        LOGGER.info("Take offer button has been clicked.");

                        Label rightLabel = new Label("\n" + ass.getTitle() + ": Do you accept the offer?\n");
                        Button confirmButton = new Button("Confirm");
                        rightVBox.getChildren().add(rightLabel);
                        rightVBox.getChildren().add(confirmButton);

                    }
                });
                hBox.getChildren().add(answerButton);
                hBox.getChildren().add(new Label("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n "+ "Status: " + ass.getStatus() + "\n "));
                listStringAss.add(hBox);
            }
            Tab selfRequestsTab = new Tab("My Requests");
            selfRequestsTab.setContent(new Label("Nothing to see there yet"));
            tabPane.getTabs().add(selfRequestsTab);
        } else {
            titleLabel.setText("List of Pending Requests");
        }


        System.out.println(role);

        ObservableList<HBox> observableListAssistance = FXCollections.observableArrayList(listStringAss);
        listAssistanceView.setItems(observableListAssistance);
    }



    public void onAddAssistanceButtonClick() throws Exception {

        LOGGER.info("Add Assistance Button has been clicked");

        CreateAssistanceLoader loader = new CreateAssistanceLoader();
        loader.start(new Stage());
        initialize();
    }

    public void onRefreshButtonClick(){

        LOGGER.info("Refresh list Button has been clicked.");

        //todo
        //listAssistanceView.refresh();
    }
}
