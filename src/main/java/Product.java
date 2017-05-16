import java.util.ArrayList;

/**
 * Created by davve on 2017-05-15.
 */
public class Product {
    public String name;
    public int price;
    public ArrayList<Ingredient> ingredientArrayList;

    public Product(String name, int price, ArrayList<Ingredient> ingredients){
        this.name = name;
        this.price = price;
        this.ingredientArrayList = ingredients;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredientArrayList;
    }

    public String getInfo(){
        return name + " " + price;
    }
}
