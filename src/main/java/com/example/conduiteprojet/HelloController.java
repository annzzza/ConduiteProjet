package com.example.conduiteprojet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    //@FXML
    //private Label welcomeText;

    @FXML
    Button HelloButton = new Button();

    /**
     * Opens the connexion/registration page and closes the Welcome page
     */
    @FXML
    protected void onHelloButtonClick(){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("choose-role-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Choose a Role");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
                Stage oldStage = (Stage) HelloButton.getScene().getWindow();
                oldStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
}
