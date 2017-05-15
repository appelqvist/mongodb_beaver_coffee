import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.CLI;

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

    public Client(String host, int port){
        //instance the client
        mongoClient = new MongoClient(host, port);

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
            choice = Integer.parseInt(JOptionPane.showInputDialog("Pick one of the alternative: "));
        }
    }
}
