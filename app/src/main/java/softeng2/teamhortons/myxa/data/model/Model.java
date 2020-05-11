package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class Model {
    @Exclude
    private String id;

    public <T extends Model> T withId(String id) {
        this.id = id;
        return (T)this;
    }

    public String getId() {
        return id;
    }
}
