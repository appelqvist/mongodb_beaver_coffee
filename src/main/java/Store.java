import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;
import java.util.LinkedList;

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

    }

}
