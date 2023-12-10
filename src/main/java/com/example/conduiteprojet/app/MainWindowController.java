package com.example.conduiteprojet.app;

import com.example.conduiteprojet.auth.User;
import com.example.conduiteprojet.auth.LogInController;
import com.example.conduiteprojet.auth.User;
import com.example.conduiteprojet.database.AssistanceDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    User.Role role = PreferencesManager.getRole();

    /**
     * Get the list that suits the user's chosen role at connection
     *
     * @return List of Assistance corresponding to the role of the connected User
     * @throws SQLException
     */
    public List<Assistance> getCorrespondingList() throws SQLException {
        if (role.equals(User.Role.BENEVOLE)) {
            //If User is a Benevole, retrieve list of Requests
            return a_daoimpl.getAssistanceRequests();
        } else if ( role.equals(User.Role.VALIDEUR)) {
            //If User is a Valideur, retrieve list of Pending Requests
            return  a_daoimpl.getAssistanceToValidate();
        } else {
            //If User is a Patient, retrieve list of Offers
            return a_daoimpl.getAssistanceOffers();
        }
    }

    /**
     * Manage Interaction with Assistance on the right panel of the window
     *
     * @param ass Assistance to be managed in right panel
     */
    public void addToRightPanel(Assistance ass)  {
        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(15,20, 10,10));
        Label rightLabel = new Label();
        if(role.equals(User.Role.BENEVOLE)) {
            rightLabel.setText("\n" + ass.getTitle() + ": Do you accept the " + ass.getType().toString() +"?\n");
        } else if(role.equals(User.Role.VALIDEUR)) {
            rightLabel.setText("\n" + ass.getTitle() + ": Do you validate the " + ass.getType().toString() +"?\n");
        }

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                LOGGER.info("Confirm button has been clicked.");

                if (role.equals(User.Role.BENEVOLE)) {
                    try {
                        ass.setStatus(Assistance.Status.FINISHED);
                        a_daoimpl.update(ass);
                        refreshAssList();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                } else if (role.equals(User.Role.VALIDEUR)) {
                    try {
                        ass.setStatus(Assistance.Status.ACCEPTED);
                        a_daoimpl.update(ass);
                        refreshAssList();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                rightLabel.setText("Accepted!");
                confirmButton.setVisible(false);
            }
        });
        rightVBox.getChildren().add(rightLabel);
        rightVBox.getChildren().add(confirmButton);
    }

    /**
     * Create HBox Element to be added to ListView
     *
     * @param ass Assistance to be arranged into an HBox element
     * @return
     */
    public HBox createListElementHBox(Assistance ass) throws SQLException {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15,20, 10,10));
        Button answerButton = new Button();
        if(role.equals(User.Role.BENEVOLE)) {
            answerButton.setText("Take");
        } else if (role.equals(User.Role.VALIDEUR)) {
            answerButton.setText("Validate");
        }
        answerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LOGGER.info( answerButton.getText() + " " + ass.getType().toString() +" button has been clicked.");
                answerButton.setDisable(false);
                addToRightPanel(ass);
            }
        });
        hBox.getChildren().add(answerButton);
        hBox.getChildren().add(new Label("\nTitle: " + ass.getTitle() + "\n" + "Description: " + ass.getDescription() + "\n" + "Created by: " + ass.getCreatorName() + "\n" + "Due date: " + ass.getDueDate().toString() + "\n"+ "Status: " + ass.getStatus() + "\n "));
        return hBox;
    }

    public void refreshAssList() throws SQLException {
        List<Assistance> listAss = getCorrespondingList();
        List<HBox> listStringAss = new ArrayList<HBox>();

        for(final Assistance ass : listAss) {
            listStringAss.add(createListElementHBox(ass));
        }
        //Create and set ListView's Generating List
        ObservableList<HBox> observableListAssistance = FXCollections.observableArrayList(listStringAss);
        listAssistanceView.setItems(observableListAssistance);
    }
    /**
     * Initialize Browsable List of Assistance in a ListView
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        LOGGER.info("Initialization of Main Window has started.");

        if (role.equals(User.Role.BENEVOLE)){
            titleLabel.setText("List of Requests");

            //Add a Tab for a Benevole to see his own Offers
            Tab selfRequestsTab = new Tab("My Offers");
            selfRequestsTab.setContent(new Label("Nothing to see there yet"));
            tabPane.getTabs().add(selfRequestsTab);
        } else if (role.equals(User.Role.PATIENT)) {
            titleLabel.setText("List of Offers");

            //Added a Tab for a Patient to see his own Requests
            Tab selfRequestsTab = new Tab("My Requests");
            selfRequestsTab.setContent(new Label("Nothing to see there yet"));
            tabPane.getTabs().add(selfRequestsTab);
        } else {

            titleLabel.setText("List of Pending Requests");
        }

        LOGGER.info("Displaying List of Assistance");
        refreshAssList();


    }


    /**
     * Button Click handler, forwards to AddAssistance View
     *
     * @throws Exception
     */
    public void onAddAssistanceButtonClick() throws Exception {

        LOGGER.info("Add Assistance Button has been clicked");

        CreateAssistanceLoader loader = new CreateAssistanceLoader();
        loader.start(new Stage());
        initialize();
    }

    /**
     * Button click handler, refreshes list of Assistance
     */
    public void onRefreshButtonClick(){
        LOGGER.info("Refresh list Button has been clicked.");

        // to be done
    }
}
