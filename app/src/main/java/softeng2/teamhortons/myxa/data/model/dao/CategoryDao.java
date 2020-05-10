package softeng2.teamhortons.myxa.data.model.dao;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class CategoryDao {
    private String desc;
    private ArrayList<DocumentReference> recipes;

    public String getDesc() {
        return desc;
    }

    public ArrayList<DocumentReference> getRecipes() {
        return recipes;
    }
}
