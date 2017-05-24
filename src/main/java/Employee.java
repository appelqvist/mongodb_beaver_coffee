import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Arrays;
import java.util.LinkedList;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by davve on 2017-05-15.
 */
public class Employee {

    private MongoCollection<Document> collection;

    public Employee(MongoDatabase mongoDatabase) {
        collection = mongoDatabase.getCollection("employee");
    }

    public void addEmployee(String name, String ssn, String salary, int discount, String position,
                            String startDate, String endDate, String workTime, ObjectId storeID) {

        Document doc = new Document("name", name)
                .append("ssn", ssn)
                .append("salary", salary)
                .append("discount", discount)
                .append("employment", new Document("position", position).append("startDate", startDate).append("endDate", endDate).append("workTime", workTime).append("storeID", storeID))
                .append("comments", Arrays.asList());

        collection.insertOne(doc);
    }

    public LinkedList<Document> getEmployees(ObjectId storeID) {
        LinkedList<Document> employees = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find(eq("employment.storeID", storeID)).iterator();

        while (cursor.hasNext()) {
            employees.add(cursor.next());
        }
        return employees;
    }

    private Document getEmployee(String employeeID) {
        Document document;
        document = collection.find(eq("_id", employeeID)).first();
        return document;
    }

    public void addComment(ObjectId employeeID, String msg, String date, String manager){
        Document comment = new Document().append("msg", msg).append("date", date).append("manager", manager);
        collection.updateOne(eq("_id", employeeID), Updates.addToSet("comments", comment));
    }
}
