package com.niallsom.passstorefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static String userToken = "";
    public static String user_id = ""; // not yet used
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("sign_in_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Pass-Store");
        stage.getIcons().add(new Image("file:C:\\Users\\Niall\\OneDrive - University of Limerick\\Desktop\\pass-store-fx\\src\\main\\resources\\com\\niallsom\\passstorefx\\Pass-Store.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}