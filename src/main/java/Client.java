import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Andréas Appelqvist on 2017-05-15.
 */
public class Client {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private Customer customerCollection;
    private Employee employeeCollection;
    private Store storeCollection;
    private Order orderCollection;
    private Product productCollection;
    private ArrayList<Order> orderList = new ArrayList<Order>();


    public Client(String host) {
        //instance the client
        mongoClient = new MongoClient(host);

        //open database
        mongoDatabase = mongoClient.getDatabase("BeaverCoffee");

        //Setting up the different collections as classes.
        customerCollection = new Customer(mongoDatabase);
        employeeCollection = new Employee(mongoDatabase);
        storeCollection = new Store(mongoDatabase);
        //managerCollection = new Manager(mongoDatabase);
        orderCollection = new Order(mongoDatabase);
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
                    "4: Chose product\n" +
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
                case 4:
                    productPick();
                    break;
                case 9:
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
            int choice = Integer.parseInt(JOptionPane.showInputDialog("" +
                    "Do purchase as:" +
                    "1: New Customer\n" +
                    "2: Old Customer \n" +
                    "0: Exit customer"));
            switch (choice) {
                case 0:
                    return;
                case 1:
                    doPurchase(false);
                    break;
                case 2:
                    doPurchase(true);
                    break;
                default:
                    break;
            }
        }
    }

    private ObjectId addCustomerPick() {
        ObjectId newCustomerId = customerCollection.addCustomer();

        int choice = Integer.parseInt(JOptionPane.showInputDialog("Want to be beavermember?\n" +
                "1: YES\n" +
                "2: NO"));
        if(choice == 1){
            addBeaverMemberPick(newCustomerId);
        }

        return newCustomerId;
    }

    private void addBeaverMemberPick(ObjectId customer){

        String ssn = JOptionPane.showInputDialog("Customers SSN:");
        String occupation = JOptionPane.showInputDialog("Customers occupation");
        ObjectId storeid = pickStoreID();
        String city = JOptionPane.showInputDialog("Customers city:");
        String street = JOptionPane.showInputDialog("Customers street:");
        String country = JOptionPane.showInputDialog("Customers country:");

        customerCollection.addBeaverClub(customer,ssn,occupation,storeid,city,street,country);
    }

    private void doPurchase(boolean old){
        ObjectId customerID;
        if(old){
            LinkedList<String> allids = customerCollection.getAllCustomers();
            String s = "Pick your id:\n";

            int i = 0;
            for(String id : allids){
                s +=  i+" : " + id + "\n";
                i ++;
            }

            int choice;
            do{
               choice = Integer.parseInt(JOptionPane.showInputDialog(s));
            }while (choice < 0 || choice > allids.size());

            customerID = new ObjectId(allids.get(choice));
        }
        else{
            customerID = addCustomerPick();
        }

        JOptionPane.showMessageDialog(null, "Du har nr:"+customerID.toString()+"\n" +
                "fortsätt gärna!");

        orderPick(pickStoreID(), customerID);
    }

    private void orderPick(ObjectId storeID, ObjectId customerID){
        int choice;
        LinkedList list = new LinkedList<Document>();
        do {
            Document product = productPick();
            list.add(product);
            choice = Integer.parseInt(JOptionPane.showInputDialog("Buy another product?\n" +
                    "1: YES!\n" +
                    "2: NO"));
        }while(choice != 2);



        Document order = new Document("storeID", storeID)
                .append("customerID", customerID)
                .append("employeeID", pickEmployeeID(storeID))
                .append("products", list);


        //Efter godkänande!!!
        orderCollection.addOrder(order); //Efter koll att inga behövs göras eller fråga en kund om du är redo
    }

    /**
     * Returns a document of ONE product
     * @return
     */
    private Document productPick() {
        Document product = new Document();
        LinkedList<Document> list = new LinkedList<>();

        int choice;
        do {
            choice = Integer.parseInt(JOptionPane.showInputDialog("1: Espresso\n" +
                    "2: Latte\n" +
                    "3: Cappuccino\n" +
                    "4: Hot Coco\n" +
                    "5: Brewed Coffee\n"));
        } while (choice <= 0 || choice > 5);

        String name = "-1";
        int price = -1;
        int milk = -1;
        int syrup = -1;
        int bean = -1;

        switch (choice) {
            case 1:
                name = "Espresso";
                bean = 1;
                price = 10;
                break;
            case 2:
                name = "Latte";
                bean = 1;
                price = 15;
                milk = milkPick();
                syrup = syrupPick();
                break;
            case 3:
                name = "Cappuccino";
                bean = 1;
                price = 15;
                milk = milkPick();
                syrup = syrupPick();
                break;
            case 4:
                name = "Hot coco";
                bean = 4;
                price = 25;
                milk = milkPick();
                syrup = syrupPick();
                break;
            case 5:
                name = "Brewed Coffee";
                bean = beanPick();
                price = 5;
                milk = milkPick();
                syrup = syrupPick();
                break;
        }

        list.add(new Document("ingredientsID", bean));
        if(milk != -1){
            list.add(new Document("ingredientsID", milk));
        }
        if(syrup != -1){
            list.add(new Document("ingredientsID", syrup));
            price += 2;
        }

        product.append("name", name)
                .append("price", price)
                .append("ingredients", list);

        return product;
    }

    private int milkPick() {
        int choice;
        choice = Integer.parseInt(JOptionPane.showInputDialog("1: Skim milk\n" +
                "2: Soy milk\n" +
                "3: Whole milk\n" +
                "4: 2% milk\n" +
                "0: Exit milk"));
        switch (choice) {
            case 0:
                return 0;
            case 1:
                return 5;
            case 2:
                return 6;
            case 3:
                return 7;
            case 4:
                return 8;
            default:
                return -1;
        }
    }

    private int syrupPick() {

        int choice;
        choice = Integer.parseInt(JOptionPane.showInputDialog("1: Vanilla\n" +
                "2: Caramel\n" +
                "3: Irish\n" +
                "0: No sirap"));
        switch (choice) {
            case 0:
                return -1;
            case 1:
                return 9;
            case 2:
                return  10;
            case 3:
                return 11;
            default:
                return -1;
        }
    }

    private int beanPick() {
        int choice;
        choice = Integer.parseInt(JOptionPane.showInputDialog("1: French roast\n" +
                "2: Light roast\n" +
                "0: Exit coffee"));
        switch (choice) {
            case 0:
                return -1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 3;
        }
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
                    } while (c < 0 || c > employees.size());
                    ObjectId employeeID = employees.get(c).getObjectId("_id");

                    String msg = JOptionPane.showInputDialog("Write your msg:");
                    String date = JOptionPane.showInputDialog("Todays date? : ");
                    String manager = JOptionPane.showInputDialog("The managers name (Your name)");

                    employeeCollection.addComment(employeeID, msg, date, manager);
                    break;
            }
        }
    }

    private ObjectId pickEmployeeID(ObjectId storeID){
        String[] names;
        ObjectId[] ids;
        LinkedList<Document> allEmployees = employeeCollection.getEmployees(storeID);

        names = new String[allEmployees.size()];
        ids = new ObjectId[allEmployees.size()];
        Document d;

        for (int i = 0; i < names.length; i++) {
            d = allEmployees.removeFirst();
            names[i] = String.valueOf(d.get("name"));
            ids[i] = (ObjectId) d.get("_id");
        }

        int id = -1;
        do {
            String out = "Select id of employee you want to handle your order:\n";
            for (int i = 0; i < names.length; i++) {
                out += i + ": " + names[i] + "\n";
            }
            id = Integer.parseInt(JOptionPane.showInputDialog(out));

        } while (id < 0 || id > names.length);

        return ids[id];
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
