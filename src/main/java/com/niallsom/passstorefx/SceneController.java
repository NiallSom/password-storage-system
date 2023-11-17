package com.niallsom.passstorefx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    public static void setScene(ActionEvent event, String sceneName){
        String fileName = sceneName + ".fxml";
        Parent root;
        try{
            root = new FXMLLoader(SceneController.class.getResource(fileName)).load();

        } catch (IOException e){
            System.out.println("That sucks lol");
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        //stage.setMaximized(true);


    }
}
