package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;

@SuppressWarnings("unused")
@IgnoreExtraProperties
public class Category {

    private String desc;
    private String tag;
    private ArrayList<Recipe> recipes;

    public Category() {
        // Default constructor required for calls to DataSnapshot.toObject(Category.class)
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
