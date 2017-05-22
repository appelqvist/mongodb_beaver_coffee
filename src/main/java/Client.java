import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andréas Appelqvist on 2017-05-15.
 */
public class Client {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private Customer customerCollection;
    private Employee employeeCollection;
    private Manager managerCollection;
    private Order orderCollection;
    private Product productCollection;
    private Stock stockCollection;
    private ArrayList<Product> productList = new ArrayList<Product>();

    public Client(String host){
        //instance the client
        mongoClient = new MongoClient(host);

        //open database
        mongoDatabase = mongoClient.getDatabase("BeaverCoffee");

        //Setting up the different collections as classes.
        //customerCollection = new Customer(mongoDatabase);
        employeeCollection = new Employee(mongoDatabase);
        //managerCollection = new Manager(mongoDatabase);
        //orderCollection = new Order(mongoDatabase);
        //productCollection = new Product(mongoDatabase);
        //stockCollection = new Stock(mongoDatabase);
    }

    /**
     * This is where the program in running
     */
    public void start(){
        while (true){
            int choice;
            choice = Integer.parseInt(JOptionPane.showInputDialog("Pick one of the alternative: \n" +
                    "1: Employee \n"+
                    "2: Customer \n"+
                    "0: Exit"));
            switch (choice){
                case 1 :
                    employeePick();
                    break;
                case 2 :
                    customerPick();
                    break;
                case 9:
                    employeeCollection.getEmployees(1);
                    break;
                case 0 :
                    System.exit(0);
                    break;
                default:
                    System.exit(0);
                    break;
            }
        }
    }

    private void customerPick(){
        while (true){
            int choice = Integer.parseInt(JOptionPane.showInputDialog("1: New Customer\n"+
                    "2: Old Customer \n"+
                    "0: Exit customer"));
            switch (choice){
                case 0 :
                    start();
                    break;
                case 1 :
                    newCustomer();
                    break;
                case 2 :
                    oldCustomer();
                    break;
                default :
                    break;
            }
        }
    }

    private void newCustomer(){
        String storeID = "Malmo3300";
        int coffeeCounter = 0;

        testList();
    }

    private void oldCustomer(){
        int cID = Integer.parseInt(JOptionPane.showInputDialog("Plix gief us your customer ID"));
    }

    private void employeePick(){
        while (true){
            int choice;
            choice = Integer.parseInt(JOptionPane.showInputDialog("1: Add employee\n"+
                                                                "0: Exit employee"));
            switch (choice){
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
                    String storeID = JOptionPane.showInputDialog("Select id of the store:\n(hämta alla store och visa här)");

                    //Why do this here? Need to make a comment in
                    String msg = "Need employee";
                    String date = startDate;
                    String eeID = "Admin";
                    employeeCollection.addEmployee(name,ssn,salary,discount,position,startDate,endDate,workingTime,storeID,msg,date,eeID);
                    break;

                case 2:
                    break;
            }
        }
    }

    private void testList(){
        try(BufferedReader br = new BufferedReader(new FileReader("src/productList.txt"))) {
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

    private void fillList(String everything){
        int choice = Integer.parseInt(JOptionPane.showInputDialog(everything));

    }

}
