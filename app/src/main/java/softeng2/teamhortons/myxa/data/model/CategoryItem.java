package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class CategoryItem {
    private String desc;
    private ArrayList<DocumentReference> recipes;
    @Exclude
    private ArrayList<RecipeItem> recipeItems;

    public CategoryItem(String desc, ArrayList<RecipeItem> recipeItems) {
        this.desc = desc;
        this.recipeItems = recipeItems;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<DocumentReference> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<DocumentReference> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<RecipeItem> getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(ArrayList<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }
}
