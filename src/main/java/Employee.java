import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.util.JSON;
import org.bson.BSON;
import org.bson.Document;

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

    public Employee(MongoDatabase mongoDatabase){
        collection = mongoDatabase.getCollection("employee");
        getEmployees("hej");
    }

    public void addEmployee(String name, String ssn, String salary, int discount, String position,
                            String startDate, String endDate, String workTime, String storeID, String msg, String date, String eeID){

        Document doc = new Document("name", name)
                .append("ssn", ssn)
                .append("salary", salary)
                .append("discount", discount)
                .append("employment", new Document("position", position).append("startDate", startDate).append("endDate", endDate)
                        .append("workTime", workTime).append("storeID", storeID))
                .append("comments", Arrays.asList("comment", new Document("msg", msg).append("date", date).append("employerID", eeID)));
        collection.insertOne(doc);
    }

    public void getEmployees(String storeID){
        //MongoCursor<Document> cursor = collection.find(eq("info.x",203)).iterator();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                employees.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    private Document getEmployee(){
        Random random = new Random();
        int randEmployee = random.nextInt(1)+ employees.size()-1;
        System.out.println(employees.get(randEmployee).get("name"));
        return employees.get(randEmployee);
    }

    public void addOrder(){

    }

    public void updateOrder(){

    }

    public void deleteOrder(){

    }

    public void getStockSize(){

    }
}
