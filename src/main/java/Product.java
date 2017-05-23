import java.util.ArrayList;

/**
 * Created by davve on 2017-05-15.
 */
public class Product {
    public String name;
    public int price;
    private int stockId;

    public Product(String name, int price, int stockId) {
        this.name = name;
        this.price = price;
        this.stockId = stockId;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getStockId(){
        return stockId;
    }

    public String getInfo(){
        return name + " " + price;
    }
}
