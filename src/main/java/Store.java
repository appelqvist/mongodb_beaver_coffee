import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Andréas Appelqvist on 2017-05-22.
 */

public class Store {
    private MongoCollection<Document> collection;

    public Store(MongoDatabase mongoDatabase){
        this.collection = mongoDatabase.getCollection("store");
    }

    public void addStore(String currency, String country, String street, String city, String zip){
        Document document = new Document("currency",currency).append("location",new Document("city", city).append("street", street).append("country", country).append("zip",zip)).append("stock", Arrays.asList());
        collection.insertOne(document);
    }

    public LinkedList<Document> getAllStores(){
        LinkedList<Document> stores = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            stores.add(cursor.next());
        }

        return stores;
    }

    public void addToStock(String storeID, String name, int unit){
        Document doc = new Document("id", "1").append("name", "espresso")
                .append("units", 10000);
        Document doc1 = new Document("name", "french roast")
                .append("units", 10000);
        Document doc2 = new Document("name", "light roast")
                .append("units", 10000);
        Document doc3 = new Document("name", "coca mix")
                .append("units", 10000);
        Document doc4 = new Document("name", "skim milk")
                .append("units", 10000);
        Document doc5 = new Document("name", "soy milk")
                .append("units", 10000);
        Document doc6 = new Document("name", "whole milk")
                .append("units", 10000);
        Document doc7 = new Document("name", "2% milk")
                .append("units", 10000);
        Document doc8 = new Document("name", "vanilla syrup")
                .append("units", 10000);
        Document doc9 = new Document("name", "caramel syrup")
                .append("units", 10000);
        Document doc10 = new Document("name", "irish cream syrup")
                .append("units", 10000);
    }


}
