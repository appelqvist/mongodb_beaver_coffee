import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

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
        Document document = new Document("currency",currency)
                .append("location",new Document("city", city)
                .append("street", street).append("country", country).append("zip",zip))
                .append("stock", Arrays.asList(
                        new Document("id","1").append("name", "espresso").append("units", 10000),
                        new Document("id","2").append("name", "french roast").append("units", 10000),
                        new Document("id","3").append("name", "light roast").append("units", 10000),
                        new Document("id","4").append("name", "coca mix").append("units", 10000),
                        new Document("id","5").append("name", "skim milk").append("units", 10000),
                        new Document("id","6").append("name", "soy milk").append("units", 10000),
                        new Document("id","7").append("name", "whole milk").append("units", 10000),
                        new Document("id","8").append("name", "2% milk").append("units", 10000),
                        new Document("id","9").append("name", "vanilla syrup").append("units", 10000),
                        new Document("id","10").append("name", "caramel syrup").append("units", 10000),
                        new Document("id","11").append("name", "irish cream syrup").append("units", 10000)
                        ));
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

    public void updateStock(ArrayList<Order> order){
        for (int i = 0; i < order.size(); i ++){
            sendToStock(order.get(i));
        }
    }

    /**
     * Need store
     * Need id that is going to be updated
     * @param order
     */
    private void sendToStock(Order order){
        int stockId = order.getProduct().getStockId();
        ObjectId objectId = order.getStoreId();
        switch (stockId){
            case 1:
              //  collection.updateOne(eq("_id", objectId));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
        }
    }

    public void fillStock(){
        collection.bulkWrite(
                Arrays.asList(new InsertOneModel<>(new Document("pID",1).append("name","esporsso").append("units", 3000)),
                new InsertOneModel<>(new Document("pID", 2).append("name", "french roast").append("units", 3000))
                ));
    }


}
