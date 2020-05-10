package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class CategoryItem {
    private String desc;
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

    public ArrayList<RecipeItem> getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(ArrayList<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }
}
