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
    private static String id = "";
    public static Document dataExists(String username){
        // this is used to check if a user already exists for sign in and sign up
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection("user_data");
            Bson bsonFilter = Filters.eq("Username", username);
            return collection.find(bsonFilter).first();
        } catch (Exception ignored) {}
        return null;
    }
    public static boolean signIn(String username, String password){
        String encPass = Crypto.encryptPasswordSHA256(password);
        Document exists = dataExists(username);
        if (exists !=null){
            id = dataExists(username).get("_id").toString();
            return dataExists(username).get("Password").equals(encPass);
        }else {
            return false;
        }

    }
    public static boolean signUp(String username, String password){
        String encPass = Crypto.encryptPasswordSHA256(password);
        if (dataExists(username) == null){
            String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase database = mongoClient.getDatabase("Pass-Store");
                MongoCollection<Document> collection = database.getCollection("user_data");
                Document data = new Document("Username",username);
                data.append("Password",encPass);
                collection.insertOne(data);
                String id = dataExists(username).get("_id").toString();
                database.createCollection(id);
                return true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return false;
    }
    /** Posts data to database**/
    public static void postData(String website, String email, String password){
        // posts data to database
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection(id);
            Document data = new Document("Website",website);
            data.append("Email", email);
            data.append("Password",Crypto.encryptAES(password,SignInController.userToken));
            System.out.println("Data post successful");
            collection.insertOne(data);

        } catch (Exception ignored) {}
    }
    /** Retrieves data from the database and returns it as a DataModel array**/
    public static DataModel[] GetData(){
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        ArrayList<DataModel> items = new ArrayList<>();
        try (
                MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection(id);

            FindIterable<Document> iterDoc = collection.find();

            for (Document document : iterDoc) {
                items.add(new DataModel((String) document.get("Website"), (String) document.get("Email"), (String) document.get("Password")));
            }
            int itemsSize = items.size();
            DataModel[] dataModels = new DataModel[itemsSize];
            for (int i=0;i<itemsSize;i++){
                dataModels[i] = items.get(i);
            }
            return dataModels;
        } catch (Exception ignored) {}
        return items.toArray(new DataModel[0]);
    }
}
