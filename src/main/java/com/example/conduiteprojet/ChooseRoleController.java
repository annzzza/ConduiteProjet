package com.example.conduiteprojet;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChooseRoleController {

    @FXML
    private ChoiceBox<String> roleBox = new ChoiceBox<String>();
    @FXML
    private TextField textFieldUsername = new TextField();
    @FXML
    private TextField textFieldLastname = new TextField();
    @FXML
    private TextField textFieldFirstname = new TextField();

    @FXML
    public void onValidateButtonClick() {
        if (roleBox.getValue() != null) {
            String chosenRole = roleBox.getValue();
            System.out.println(chosenRole);

            String chosenUsername = textFieldUsername.getText();
            String chosenLastname = textFieldLastname.getText();
            String chosenFirstname = textFieldFirstname.getText();

            User newUser = new User();
            newUser.setRole(chosenRole);
            newUser.setLastName(chosenLastname);
            newUser.setFirstName(chosenFirstname);
            newUser.setUsername(chosenUsername);

            UserDaoImplementation userDaoImplementation = new UserDaoImplementation();
            try {
                userDaoImplementation.add(newUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } else {
            System.out.println("Choisir une valeur.");
            //go to new page
        }
    }
}
