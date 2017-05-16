import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javax.swing.*;

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
                    "1: Employee"));
            switch (choice){
                case 1 :
                    employeePick();
                    break;
                default:
                    System.exit(0);
                    break;
            }
        }
    }


    private void employeePick(){
        while (true){
            int choice;
            choice = Integer.parseInt(JOptionPane.showInputDialog("1: Add employee\n"+
                                                                "0: Exit employee"));
            switch (choice){
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
                    employeeCollection.addEmployee(name,ssn,salary,discount,position,startDate,endDate,workingTime,storeID);
                    break;

                case 2:
                    break;
            }
        }
    }
}
