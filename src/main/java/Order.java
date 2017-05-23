import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by davve on 2017-05-15.
 */
public class Order {
    private ObjectId storeId;
    private ObjectId eId;
    private ObjectId cId;
    private String date;
    private Product product;

    public Order(ObjectId storeId, ObjectId eId, ObjectId cId, String date, Product product) {
        this.storeId = storeId;
        this.eId = eId;
        this.cId = cId;
        this.date = date;
        this.product = product;
    }

    public ObjectId getStoreId(){
        return storeId;
    }

    public ObjectId geteId(){
        return eId;
    }

    public ObjectId getcId(){
        return cId;
    }

    public String getDate(){
        return date;
    }

    public Product getProduct(){
        return product;
    }
}
