package softeng2.teamhortons.myxa.data.model;

public abstract class Dao {
    private String id;

    Dao() {
        //ignore
    }

    Dao(String id) {
        this.id = id;
    }

    public void id(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
