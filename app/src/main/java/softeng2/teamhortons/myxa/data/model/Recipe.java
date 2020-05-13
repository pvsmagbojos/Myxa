package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private String name;
    private double price;
    private String imgUri;
    private String imgUriPreview;
    private ArrayList<String> procedure;
    private HashMap<String,String> recipe_ingredients;
    private ArrayList<String> tags;

    @SuppressWarnings("unused")
    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImgUri() {
        return imgUri;
    }

    public String getImgUriPreview() {
        return imgUriPreview;
    }

    public ArrayList<String> getProcedure() {
        return procedure;
    }

    public HashMap<String, String> getRecipe_ingredients() {
        return recipe_ingredients;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
