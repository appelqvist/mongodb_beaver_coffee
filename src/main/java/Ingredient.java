/**
 * Created by davve on 2017-05-16.
 */
public class Ingredient {
    private String id;
    private int unit;

    public Ingredient(String id, int unit){
        this.id = id;
        this.unit = unit;
    }

    public String getId(){
        return id;
    }

    public int getUnit(){
        return unit;
    }
}
