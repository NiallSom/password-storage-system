package com.niallsom.passstorefx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    private Button signUp;
    @FXML
    private Hyperlink login;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    public void initialize(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        DatabaseHandling databaseHandling = new DatabaseHandling("user_data");
        signUp.setOnAction((e)->{
            if (databaseHandling.signUp(username.getText(),password.getText())){
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
