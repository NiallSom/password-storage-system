package com.niallsom.passstorefx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

public class SignInController {
    public static String userToken;

    @FXML
    private Hyperlink signUp;
    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    DatabaseHandling databaseHandling = new DatabaseHandling("user_data");
    public void initialize(){
        signUp.setOnAction((e)-> SceneController.setScene(e,"sign_up_view"));
        login.setOnAction((e)->{
            if (databaseHandling.signIn(username.getText(),password.getText())){
                userToken = password.getText();// set user token to the login password so stored data can be decrypted
                SceneController.setScene(e,"main_screen"); // switch scene to main_screen
            }else {
                alert.setContentText("Sorry incorrect username or password");
                alert.show();// show alert
            }

        });
    }

}