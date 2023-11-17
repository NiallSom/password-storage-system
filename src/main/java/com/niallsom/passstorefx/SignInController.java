package com.niallsom.passstorefx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignInController {
    public static String userToken;
    @FXML
    private Button signUp;
    @FXML
    private Button login;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    public void initialize(){
        signUp.setOnAction((e)->{
            SceneController.setScene(e,"sign_up_view");
        });
        login.setOnAction((e)->{
            //SceneController.setScene(e,"selection_menu");
            //Nially,123
            if (DatabaseHandling.signIn(username.getText(),password.getText())){
                userToken = password.getText();
                SceneController.setScene(e,"main_screen");
            }else {
                alert.setContentText("Sorry incorrect username or password");
                alert.show();
            }

        });

    }

}