package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Category {

    private String desc;
    private String tag;
    @Exclude
    private ArrayList<Recipe> recipes;

    @SuppressWarnings("unused")
    public Category() {
        // Default constructor required for calls to DataSnapshot.getValue(Category.class)
    }

    public Category(String desc, ArrayList<Recipe> recipes) {
        this.desc = desc;
        this.recipes = recipes;
    }

    public String getDesc() {
        return desc;
    }

    public String getTag() {
        return tag;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
