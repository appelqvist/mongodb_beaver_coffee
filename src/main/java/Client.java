import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.bson.Document;
import org.bson.types.ObjectId;
import sun.awt.image.ImageWatched;

import javax.print.Doc;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Andréas Appelqvist on 2017-05-15.
 */
public class Client {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private Customer customerCollection;
    private Employee employeeCollection;
    private Store storeCollection;
    private Manager managerCollection;
    private Order orderCollection;
    private Product productCollection;
    private Stock stockCollection;
    private ArrayList<Product> productList = new ArrayList<Product>();

    public Client(String host) {
        //instance the client
        mongoClient = new MongoClient(host);

        //open database
        mongoDatabase = mongoClient.getDatabase("BeaverCoffee");

        //Setting up the different collections as classes.
        //customerCollection = new Customer(mongoDatabase);
        employeeCollection = new Employee(mongoDatabase);
        storeCollection = new Store(mongoDatabase);
        //managerCollection = new Manager(mongoDatabase);
        //orderCollection = new Order(mongoDatabase);
        //productCollection = new Product(mongoDatabase);
        //stockCollection = new Stock(mongoDatabase);
    }

    /**
     * This is where the program in running
     */
    public void start() {
        while (true) {
            int choice;
            choice = Integer.parseInt(JOptionPane.showInputDialog("Pick one of the alternative: \n" +
                    "1: Employee \n" +
                    "2: Customer \n" +
                    "3: Store \n" +
                    "0: Exit"));
            switch (choice) {
                case 1:
                    employeePick();
                    break;
                case 2:
                    customerPick();
                    break;
                case 3:
                    storePick();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.exit(0);
                    break;
            }
        }
    }

    private void storePick() {
        while (true) {
            int choice = Integer.parseInt(JOptionPane.showInputDialog("1: Add new store\n" +
                    "2: Bla bla\n" +
                    "0: Exit store section "));
            switch (choice) {
                case 1:
                    String city = JOptionPane.showInputDialog("City where the store is located:");
                    String street = JOptionPane.showInputDialog("Street:");
                    String country = JOptionPane.showInputDialog("Country");
                    String zip = JOptionPane.showInputDialog("Zip-code:");
                    String currency = JOptionPane.showInputDialog("Currency used in store:");
                    storeCollection.addStore(currency, country, street, city, zip);
                    break;
                case 2:
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    private void customerPick() {
        while (true) {
            int choice = Integer.parseInt(JOptionPane.showInputDialog("1: New Customer\n" +
                    "2: Old Customer \n" +
                    "0: Exit customer"));
            switch (choice) {
                case 0:
                    start();
                    break;
                case 1:
                    newCustomer();
                    break;
                case 2:
                    oldCustomer();
                    break;
                default:
                    break;
            }
        }
    }

    private void newCustomer() {
        String storeID = "Malmo3300";
        int coffeeCounter = 0;

        testList();
    }

    private void oldCustomer() {
        int cID = Integer.parseInt(JOptionPane.showInputDialog("Plix gief us your customer ID"));
    }

    private void employeePick() {
        while (true) {
            int choice;
            choice = Integer.parseInt(JOptionPane.showInputDialog("1: Add employee\n" +
                    "2: Set comment on a employee\n" +
                    "0: Exit employee"));
            switch (choice) {
                case 0:
                    start();
                    break;
                case 1:
                    //Method??
                    String name = JOptionPane.showInputDialog("Choose name:");
                    String ssn = JOptionPane.showInputDialog("Choose SSN:");
                    String salary = JOptionPane.showInputDialog("Choose salary:");
                    int discount = Integer.parseInt(JOptionPane.showInputDialog("Choose discount:"));
                    String position = JOptionPane.showInputDialog("Choose position:");
                    String startDate = JOptionPane.showInputDialog("Choose start date:\n" +
                            "Format: YYYY-MM-DD");
                    String endDate = JOptionPane.showInputDialog("Choose end date:\n" +
                            "Format: YYYY-MM-DD");
                    String workingTime = JOptionPane.showInputDialog("Choose working time (%):");
                    employeeCollection.addEmployee(name, ssn, salary, discount, position, startDate, endDate, workingTime, pickStoreID());
                    break;

                case 2:
                    ObjectId store = pickStoreID();
                    LinkedList<Document> employees = employeeCollection.getEmployees(store);
                    String select = "Select one employee:\n";
                    int i = 0;
                    for (Document d : employees) {
                        select += i + " : " + d.get("name");
                        i++;
                    }

                    int c;
                    do {
                        c = Integer.parseInt(JOptionPane.showInputDialog(select));
                    }while (c < 0 || c > employees.size());
                    ObjectId employeeID = employees.get(c).getObjectId("_id");

                    String msg = JOptionPane.showInputDialog("Write your msg:");
                    String date = JOptionPane.showInputDialog("Todays date? : ");
                    String manager = JOptionPane.showInputDialog("The managers name (Your name)");

                    employeeCollection.addComment(employeeID, msg, date, manager);
                    break;
            }
        }
    }

    private ObjectId pickStoreID() {
        String[] storeCityStreet;
        ObjectId[] ids;
        LinkedList<Document> allStores = storeCollection.getAllStores();

        storeCityStreet = new String[allStores.size()];
        ids = new ObjectId[allStores.size()];
        Document d;

        for (int i = 0; i < storeCityStreet.length; i++) {
            d = allStores.removeFirst();
            storeCityStreet[i] = String.valueOf(d.get("location"));
            ids[i] = (ObjectId) d.get("_id");
        }

        int id = -1;
        do {
            String out = "Select id of store:\n";
            for (int i = 0; i < storeCityStreet.length; i++) {
                out += i + ": " + storeCityStreet[i] + "\n";
            }
            id = Integer.parseInt(JOptionPane.showInputDialog(out));

        } while (id < 0 || id > storeCityStreet.length);

        return ids[id];
    }

    private void testList() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/productList.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            fillList(everything);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillList(String everything) {
        int choice = Integer.parseInt(JOptionPane.showInputDialog(everything));

    }

}
