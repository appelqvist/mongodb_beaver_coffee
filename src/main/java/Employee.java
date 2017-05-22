import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.util.JSON;
import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import sun.awt.image.ImageWatched;

import javax.print.Doc;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by davve on 2017-05-15.
 */
public class Employee {
    MongoCollection<Document> collection;
    LinkedList<Document> employees = new LinkedList();

    public Employee(MongoDatabase mongoDatabase) {
        collection = mongoDatabase.getCollection("employee");
    }

    public void addEmployee(String name, String ssn, String salary, int discount, String position,
                            String startDate, String endDate, String workTime, String storeID,
                            String msg, String date, String eeID) {

        Document doc = new Document("name", name)
                .append("ssn", ssn)
                .append("salary", salary)
                .append("discount", discount)
                .append("employment", new Document("position", position).append("startDate", startDate).append("endDate", endDate).append("workTime", workTime).append("storeID", storeID))
                .append("comments", Arrays.asList());


        collection.insertOne(doc);
    }

    public LinkedList<Document> getEmployees(int storeID) {
        LinkedList<Document> employees = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find(eq("employment.storeID", String.valueOf(storeID))).iterator();

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

    public void addComment(String employeeID, String msg, String date, String emplyerID){
        //Arrays.asList(new Document("msg", msg).append("date", date).append("employerID", eeID))
        //db.employee.update( { "_id":ObjectId("5922ae9f0774c10f30bf1c10") }, { $push: { "comments": {"msg":"Det här nytt7", "date":"131sd123132", "employerID":"pelle1"} } } )

        //collection.updateOne(eq("_id");
    }

    public void addOrder() {

    }

    public void updateOrder() {

    }

    public void deleteOrder() {

    }

    public void getStockSize() {

    }
}


/*
	"comments" : [
            "comment" {
            "msg" : "Need employee",
            "date" : "2016-04-12",
            "employerID" : "Admin"
            },
            "comment": {
                "msg" : "hej"
                "date" : "date"
                "empleyerID" : "Admin"
            }

*/