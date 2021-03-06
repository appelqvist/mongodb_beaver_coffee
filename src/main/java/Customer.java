import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.LinkedList;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by davve on 2017-05-15.
 * Edit by Andréas
 */
public class Customer {

    private MongoCollection<Document> collection;

    public Customer(MongoDatabase database) {
        collection = database.getCollection("customer");
    }

    public ObjectId addCustomer() {
        Document d = new Document("coffeeCounter", 0)
                .append("beaverClub", Arrays.asList())
                .append("orders", Arrays.asList());

        collection.insertOne(d);
        ObjectId id = d.getObjectId("_id");
        return id;
    }

    public void addBeaverClub(ObjectId customer, String ssn, String occupation, ObjectId storeID, String city, String street, String country) {
        Document beaverClub = new Document()
                .append("ssn", ssn)
                .append("occupation", occupation)
                .append("storeID", storeID)
                .append("address", Arrays.asList(
                        new Document("city", city)
                                .append("street", street)
                                .append("country", country)));

        collection.updateOne(eq("_id", customer), Updates.addToSet("beaverClub", beaverClub));
    }

    public void addOrder(int customerID, int orderID) {

    }

    public LinkedList<String> getAllCustomers() {
        LinkedList<String> ids = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            ids.add(cursor.next().get("_id").toString());
        }
        return ids;
    }
}
