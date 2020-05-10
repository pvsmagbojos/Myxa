package softeng2.teamhortons.myxa.data.model;

import java.util.HashMap;

public abstract class Model {
    private String id;

    Model(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract Object toDao();
}
