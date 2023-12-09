package com.example.conduiteprojet.auth;

import com.example.conduiteprojet.app.MainWindowLoader;
import com.example.conduiteprojet.database.UserDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import com.example.conduiteprojet.utils.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LogInController {
    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    public TextField usernameField;
    public TextField passwordField;
    public Button LoginButton;
    public Button RegisterButton;
    public GridPane gridPane;
    @FXML
    public Label message;

    /**
     * Validate data and show error messages if needed.
     *
     * @return whether the data is valid or not
     */
    protected boolean dataValidation() {
        boolean isDataValid = true;

        // Get fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Reset messageLabel
        message.setText("");

        // Check whether username field is valid
        if (username == null || username.isEmpty()) {
            message.setText("Please fill username.");
            message.setTextFill(Color.rgb(210, 39, 30));
            isDataValid = false;
        }
        // Check whether password field is valid
        if (password == null || password.isEmpty()) {
            message.setText("Please fill password.");
            message.setTextFill(Color.rgb(210, 39, 30));
            isDataValid = false;
        }

        return isDataValid;
    }

    /**
     * Try to log in.
     * When the user is logged in, its id is saved.
     * @param username User username
     * @param password User password in clear
     * @return whether the user has been connected
     */
    public static boolean login(String username, String password) {
        // Get user from username from DB
        UserDaoImplementation udi = new UserDaoImplementation();
        try {
            User user = udi.getUser(username);
            if (user == null) {
                LOGGER.info(String.format("User with username '%s' not found.", username));
                return false;
            }
            LOGGER.info(String.format("User with username '%s' found.", username));

            if (Objects.equals(Security.getMd5(password), user.getPassword())) {
                LOGGER.info("Credentials are valid.");
                PreferencesManager.saveID(user.getId());
                PreferencesManager.saveRole(user.getRole());
                LOGGER.info(("User id saved to preferences."));
                return true;
            } else {
                LOGGER.info("Credentials are wrong.");
                return false;
            }

        } catch (SQLException e) {
            LOGGER.error("SQL Exception:" + e);
            return false;
        }
    }

    /**
     * Try to log in using username and password field.
     */
    @FXML
    protected void onLoginButtonClick() {
        // If data is valid -> try log in
        if (dataValidation()) {
            // Get fields
            String username = usernameField.getText();
            String password = passwordField.getText();

            if(login(username, password)) {
                message.setText("Connected!");

                try {
                    MainWindowLoader mwl = new MainWindowLoader();
                    mwl.start(new Stage());
                } catch (Exception e) {
                    LOGGER.error("Cannot open Main Window");
                    message.setText("Something went wrong.");
                }

                this.close();
            } else {
                message.setText("Wrong username or password.");
                message.setTextFill(Color.rgb(210, 39, 30));
            }
        }
    }

    /**
     * Opens the register page.
     */
    public void onRegisterButtonClick() {
        try {
            RegisterLoader rl = new RegisterLoader();
            rl.start(new Stage());

            this.close();
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * Close this window.
     */
    private void close() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }
}
