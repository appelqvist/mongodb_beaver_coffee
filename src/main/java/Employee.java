import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.util.JSON;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by davve on 2017-05-15.
 */
public class Employee {
    MongoCollection<Document> collection;

    public Employee(MongoDatabase mongoDatabase){
        collection = mongoDatabase.getCollection("employee");
        getEmployees("hej");
    }

    public void addEmployee(String name, String ssn, String salary, int discount, String position,
                            String startDate, String endDate, String workTime, String storeID){

        Document doc = new Document("name", name)
                .append("ssn", ssn)
                .append("salary", salary)
                .append("discount", discount)
                .append("employment", new Document("position", position).append("startDate", startDate).append("endDate", endDate).append("workTime", workTime).append("storeID", storeID));
        collection.insertOne(doc);
    }

    public void getEmployees(String storeID){
        MongoCursor<Document> cursor = collection.find(eq("info.x",203)).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

    }
}
