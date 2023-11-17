package com.niallsom.passstorefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainScreenController {
    @FXML
    TableView<DataModel> table = new TableView<>();
    @FXML
    private Button submit;
    @FXML
    private Button sign_out;
    @FXML
    private TextField websiteData;
    @FXML
    private TextField emailData;
    @FXML
    private TextField passwordData;
    @FXML
    private TableColumn<DataModel,String> websiteColumn;
    @FXML
    private TableColumn<DataModel,String> emailColumn;
    @FXML
    private TableColumn<DataModel,String> passwordColumn;
    public void initialize() {
       updateTable();
        sign_out.setOnAction((e)-> SceneController.setScene(e,"sign_in_view"));
        submit.setOnAction((e)-> {
            // posting data to database
            DatabaseHandling.postData(websiteData.getText(),emailData.getText(),passwordData.getText());
            updateTable();
            // clearing TextFields
            websiteData.clear();
            emailData.clear();
            passwordData.clear();
        });
    }
    public void updateTable(){
        // this will get the data from the database and put it in the tableview
        table.getItems().clear();
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("Website"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        for (DataModel data: DatabaseHandling.GetData()){
            table.getItems().add(new DataModel(data.getWebsite(),data.getEmail(),Crypto.decryptAES(data.getPassword(), SignInController.userToken)));
        }
    }
}
