package com.niallsom.passstorefx;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;


public class DatabaseHandling {
    private static final String key = System.getenv("database_password");
    public static Document dataExists(String username){
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection("user_data");
            Bson bsonFilter = Filters.eq("Username", username);
            Document exists = collection.find(bsonFilter).first();
            return collection.find(bsonFilter).first();
        } catch (Exception ignored) {}
        //return this.collection.find(filter).first();
        return null;
    }
    public static boolean signIn(String username, String password){
        System.out.println(key);
        String encPass = Crypto.encryptPasswordSHA256(password);
        return dataExists(username).get("Password").equals(encPass);
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

                return true;
            } catch (Exception ignored) {}
        }else {
            System.out.println("Nope");
        }
        return false;
    }
    public static void postData(String website, String email, String password){
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection("data");
            Document data = new Document("Website",website);
            data.append("Email", email);
            data.append("Password",Crypto.encryptAES(password,SignInController.userToken));
            System.out.println("Data post successful");
            collection.insertOne(data);

        } catch (Exception ignored) {}
    }
    public static DataModel[] GetData(){
        String uri = "mongodb+srv://Admin:"+key+"@cluster0.tlsr9al.mongodb.net/?retryWrites=true&w=majority";
        ArrayList<DataModel> items = new ArrayList<>();
        try (
                MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("Pass-Store");
            MongoCollection<Document> collection = database.getCollection("data");

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
