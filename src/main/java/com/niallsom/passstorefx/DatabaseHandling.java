package com.niallsom.passstorefx;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;


public class DatabaseHandling {
    // MAKE THIS CLASS OBJECT ORIENTATED
    private static final String key = System.getenv("database_password");
    public static String id = "";
    private MongoCollection<Document> collection;
    private MongoDatabase database;
    public DatabaseHandling(String collectionID){
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("Pass-Store");
        this.collection = database.getCollection(collectionID);
    }
    public Document dataExists(String username){
        // this is used to check if a user already exists for sign in and sign up
        // userdata
        Bson bsonFilter = Filters.eq("Username", username);
        return this.collection.find(bsonFilter).first();
    }
    public boolean signIn(String username, String password){
        String encPass = Crypto.encryptPasswordSHA256(password);
        Document exists = dataExists(username);
        if (exists !=null){
            id = dataExists(username).get("_id").toString();
            return dataExists(username).get("Password").equals(encPass);
        }else {
            return false;
        }

    }
    public boolean signUp(String username, String password){
        String encPass = Crypto.encryptPasswordSHA256(password);
        if (dataExists(username) == null){
                Document data = new Document("Username",username);
                data.append("Password",encPass);
                this.collection.insertOne(data);
                String id = dataExists(username).get("_id").toString();
                this.database.createCollection(id);
                return true;
            }
        return false;
    }
    /** Posts data to database**/
    public void postData(String website, String email, String password){
        // posts data to database
        Document data = new Document("Website",website);
        data.append("Email", email);
        data.append("Password",Crypto.encryptAES(password,SignInController.userToken));
        System.out.println("Data post successful");
        this.collection.insertOne(data);
    }
    /** Retrieves data from the database and returns it as a DataModel array**/
    public DataModel[] GetData(){
        ArrayList<DataModel> items = new ArrayList<>();
            FindIterable<Document> iterDoc = this.collection.find();
            for (Document document : iterDoc) {
                items.add(new DataModel((String) document.get("Website"), (String) document.get("Email"), (String) document.get("Password")));
            }
            int itemsSize = items.size();
            DataModel[] dataModels = new DataModel[itemsSize];
            for (int i=0;i<itemsSize;i++){
                dataModels[i] = items.get(i);
            }
            return dataModels;
    }
}
