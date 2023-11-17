package com.niallsom.passstorefx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    private Button signUp;
    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    public void initialize(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        signUp.setOnAction((e)->{
            if (DatabaseHandling.signUp(username.getText(),password.getText())){
                SceneController.setScene(e, "sign_in_view");
            }else {
                a.setContentText("Sorry that user already exists!");
                a.show();
            }
        });
        login.setOnAction((e)->{
            SceneController.setScene(e,"sign_in_view");
        });
    }
}
