import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;

/**
 * Created by davve on 2017-05-15.
 */
public class Order {
    private MongoCollection<Document> collection;

    public Order(MongoDatabase database) {
        collection = database.getCollection("order");
    }

    public void addOrder(Document d){
        collection.insertOne(d);
    }
}
