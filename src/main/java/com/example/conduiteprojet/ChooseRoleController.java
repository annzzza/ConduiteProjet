package com.example.conduiteprojet;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ChooseRoleController {

    @FXML
    private ChoiceBox<String> roleBox = new ChoiceBox<String>();

    @FXML
    public void onValidateButtonClick(){
        if (roleBox.getValue() != null) {
            String role = roleBox.getValue();
            System.out.println(role);
        } else {
            System.out.println("Choisir une valeur.");
            //go to new page
        }
    }
}
