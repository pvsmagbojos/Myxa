package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.PropertyName;

public class Recipe {
    @PropertyName("imgUriPreview")
    private String imgUriPreview;

    @SuppressWarnings("unused")
    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public String getImgUriPreview() {
        return imgUriPreview;
    }
}
