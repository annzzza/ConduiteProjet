package com.example.conduiteprojet.auth;


import com.example.conduiteprojet.database.UserDaoImplementation;
import com.example.conduiteprojet.utils.Security;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;


public class RegisterController {
    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

    public Label errorLabel;
    @FXML
    private HBox root = new HBox();

    @FXML
    private ChoiceBox<String> roleBox = new ChoiceBox<>();
    @FXML
    private TextField textFieldUsername = new TextField();
    @FXML
    private TextField textFieldPassword = new TextField();
    @FXML
    private TextField textFieldLastname = new TextField();
    @FXML
    private TextField textFieldFirstname = new TextField();

    @FXML
    private Button validateButton = new Button();
    @FXML
    private Label welcomeLabel = new Label();
    @FXML
    private Button pursueButton = new Button();

    @FXML
    public void onPursueButtonClick() throws Exception {
        LogInLoader ll = new LogInLoader();
        ll.start(new Stage());

        this.close();
    }

    /**
     * Validates data for registration
     *
     * @return true if the data is valid.
     */
    public boolean validateRegisterData() {
        boolean isDataValid = true;
        errorLabel.setTextFill(Color.rgb(255, 0, 0));
        if (roleBox.getValue() == null) {
            errorLabel.setText("Select a role.");
            isDataValid = false;
        }
        if (textFieldFirstname.getText().isEmpty()) {
            errorLabel.setText("Firstname field is empty.");
            isDataValid = false;
        }
        if (textFieldLastname.getText().isEmpty()) {
            errorLabel.setText("Lastname field is empty.");
            isDataValid = false;
        }
        if (textFieldUsername.getText().isEmpty()) {
            errorLabel.setText("Username field is empty");
            isDataValid = false;
        }
        if (textFieldPassword.getText().isEmpty()) {
            errorLabel.setText("Password field is empty");
            isDataValid = false;
        }
        if (textFieldPassword.getText().length() < 6) {
            errorLabel.setText("Password should be longer than 6 characters.");
            isDataValid = false;
        }

        return isDataValid;
    }

    /**
     * Register a user given data
     *
     * @param user User with data (should have been validated before)
     * @return whether the user has been registered
     */
    public static boolean register(User user) {
        try {
            UserDaoImplementation udi = new UserDaoImplementation();
            if (udi.getUser(user.getUsername()) == null) {
                udi.add(user);
                LOGGER.info(String.format("User has been registered: %s", user.getUsername()));
                return true;
            } else {
                LOGGER.info(String.format("User has not been registered, it already exists: %s", user.getUsername()));
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception: " + e);
            return false;
        }
    }

    /**
     * Sends new User to database with first name, last name, username, password and role.
     * A Role MUST be picked.
     */
    @FXML
    public void onValidateButtonClick() {
        errorLabel.setText("");
        if (validateRegisterData()) {
            User newUser = new User();
            newUser.setUsername(textFieldUsername.getText());
            newUser.setFirstName(textFieldFirstname.getText());
            newUser.setLastName(textFieldLastname.getText());
            newUser.setPassword(Security.getMd5(textFieldPassword.getText()));
            newUser.setRole(User.Role.valueOf(roleBox.getValue().toUpperCase()));


            if (register(newUser)) {
                root.getChildren().remove(validateButton);
                textFieldFirstname.setEditable(false);
                textFieldLastname.setEditable(false);
                textFieldUsername.setEditable(false);
                textFieldPassword.setEditable(false);
                welcomeLabel.setText("Welcome " + textFieldFirstname.getText() + "!");
                welcomeLabel.setTextFill(Color.rgb(74, 254, 0));

                pursueButton.setVisible(true);
            } else {
                errorLabel.setText("The username is already taken.");
            }
        }
    }

    /**
     * Close this window
     */
    private void close() {
        Stage stage = (Stage) pursueButton.getScene().getWindow();
        stage.close();
    }
}
