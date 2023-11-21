package com.example.conduiteprojet;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.prefs.Preferences;


public class ChooseRoleController {
    @FXML
    private HBox root = new HBox();

    @FXML
    private ChoiceBox<String> roleBox = new ChoiceBox<String>();
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

    /**
     * @param input String : password entered by user
     * @return hashed string
     */
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onPursueButtonClick() throws Exception {
        BrowserLoader bl = new BrowserLoader();
        bl.start(new Stage());
    }

    /**
     * Sends new User to database with first name, last name, username, password and role.
     * A Role MUST be picked.
     */
    @FXML
    public void onValidateButtonClick() {
        if (roleBox.getValue() != null) {
            String chosenRole = roleBox.getValue();
            String chosenUsername = textFieldUsername.getText();
            String chosenLastname = textFieldLastname.getText();
            String chosenFirstname = textFieldFirstname.getText();
            String chosenPasswordHashed = getMd5(textFieldPassword.getText());

            User newUser = new User();
            newUser.setRole(chosenRole);
            newUser.setLastName(chosenLastname);
            newUser.setFirstName(chosenFirstname);
            newUser.setUsername(chosenUsername);
            newUser.setPassword(chosenPasswordHashed);

            UserDaoImplementation userDaoImplementation = new UserDaoImplementation();
            try {
                userDaoImplementation.add(newUser);
                root.getChildren().remove(validateButton);
                textFieldFirstname.setEditable(false);
                textFieldLastname.setEditable(false);
                textFieldUsername.setEditable(false);
                textFieldPassword.setEditable(false);
                welcomeLabel.setText("Welcome " + chosenFirstname + "!");
                pursueButton.setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            PreferencesManager.saveRole(chosenRole);

        } else {
            welcomeLabel.setText("Choisir une valeur.");
        }
    }
}
