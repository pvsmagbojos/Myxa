package softeng2.teamhortons.myxa.data.model;

import java.util.HashMap;

public abstract class Dao {
    private String userId;

    Dao() {
        //ignore
    }

    Dao(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
