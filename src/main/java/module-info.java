module com.niallsom.passstorefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;


    opens com.niallsom.passstorefx to javafx.fxml;
    exports com.niallsom.passstorefx;
}