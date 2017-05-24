import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.Updates;
import com.sun.tools.javac.util.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import sun.awt.SunHints;
import sun.awt.image.ImageWatched;
import sun.rmi.server.InactiveGroupException;

import java.util.*;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Andréas Appelqvist on 2017-05-22.
 */

public class Store {
    private MongoCollection<Document> collection;

    public Store(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("store");
    }

    public void addStore(String currency, String country, String street, String city, String zip) {
        Document document = new Document("currency", currency)
                .append("location", new Document("city", city)
                        .append("street", street).append("country", country).append("zip", zip))
                .append("stock", Arrays.asList(
                        new Document("id", "1").append("name", "espresso").append("units", 10000),
                        new Document("id", "2").append("name", "french roast").append("units", 10000),
                        new Document("id", "3").append("name", "light roast").append("units", 10000),
                        new Document("id", "4").append("name", "coca mix").append("units", 10000),
                        new Document("id", "5").append("name", "skim milk").append("units", 10000),
                        new Document("id", "6").append("name", "soy milk").append("units", 10000),
                        new Document("id", "7").append("name", "whole milk").append("units", 10000),
                        new Document("id", "8").append("name", "2% milk").append("units", 10000),
                        new Document("id", "9").append("name", "vanilla syrup").append("units", 10000),
                        new Document("id", "10").append("name", "caramel syrup").append("units", 10000),
                        new Document("id", "11").append("name", "irish cream syrup").append("units", 10000)
                ));
        collection.insertOne(document);
    }

    public LinkedList<Document> getAllStores() {
        LinkedList<Document> stores = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            stores.add(cursor.next());
        }
        return stores;
    }

    public void removeFromStock(ObjectId storeId, LinkedList<Document> productsBought) {
        HashMap<Integer, Integer> stockitems = new HashMap<>();

        List<Document> ingred;
        int id = -1;
        for (Document d : productsBought) {
            ingred = (List<Document>) d.get("ingredients");
            for (Document i : ingred) {
                try {
                    id = Integer.parseInt(String.valueOf(i.get("ingredientsID")));
                    stockitems.put(id, stockitems.get(id) + 1);
                }catch (NullPointerException e){
                    stockitems.put(id, 1);
                }
            }
        }

        for (int key : stockitems.keySet()) {
            System.out.println(key + " dra av: " + stockitems.get(key));

            /*
            BasicDBObject q = new BasicDBObject("_id", storeId);
            q.append("stock.id", key);

            BasicDBObject u = new BasicDBObject().append("$inc",
                    new BasicDBObject().append("units", -1 * stockitems.get(key)));

            collection.updateOne(q, u);
            */

            BasicDBObject query = new BasicDBObject();
            query.put("_id", storeId);
            query.append("stock id", 1);

            BasicDBObject update = new BasicDBObject()
                    .append("$inc", new BasicDBObject().append("units", -1));

            collection.updateOne(query, update);
/*
            {
                "_id"  :  {
                        "$oid" : "x"
                 } ,
                "hosting" : "hostA" ,
                "clients" : "888" ,
                "type" : "vps"

            }

            {
                "_id"  :   {
                        "$oid" : "x"
                } ,
                "hosting" : "hostB" ,
                "type" : "dedicated server" ,
                "clients" : 100
            }
       */
        }

    }

}
