package com.example.conduiteprojet.auth;
import com.example.conduiteprojet.app.MainWindowLoader;
import com.example.conduiteprojet.database.UserDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
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
     * Try to log in using username and password field.
     */
    @FXML
    protected void onLoginButtonClick() {

        // Data Validation
        String username = usernameField.getText();
        String password = passwordField.getText();
        message.setText("");
        boolean errorDetected = false;
        if(username == null || username.isEmpty()) {
            message.setText("Please fill username.");
            message.setTextFill(Color.rgb(210, 39, 30));
            errorDetected = true;
        }
        if(password == null || password.isEmpty()) {
            message.setText("Please fill password.");
            message.setTextFill(Color.rgb(210, 39, 30));
            errorDetected = true;
        }

        // If data is valid -> try log in
        if(!errorDetected) {
            UserDaoImplementation udi = new UserDaoImplementation();
            try {
                User user = udi.getUser(username);
                if(Objects.equals(RegisterController.getMd5(password), user.getPassword())) {
                    message.setText("Connected!");
                    PreferencesManager.saveUserID(user.getId());
                    PreferencesManager.saveRole(user.getRole().toString());

                    LOGGER.info("Connected User with ID : " + PreferencesManager.getUserID() + " and with Role : " + PreferencesManager.getRole());

                    MainWindowLoader mwl = new MainWindowLoader();
                    mwl.start(new Stage());
                } else {
                    message.setText("Wrong username or password.");
                    message.setTextFill(Color.rgb(210, 39, 30));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Opens the register page.
     */
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        try {
            RegisterLoader rl = new RegisterLoader();
            rl.start(new Stage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
