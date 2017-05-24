import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.Date;

/**
 * Created by Andréas Appelqvist on 2017-05-11.
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting up mongo-db..");
        new Client("localhost").start();

    }
}
