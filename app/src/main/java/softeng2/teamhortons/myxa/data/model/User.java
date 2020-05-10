package softeng2.teamhortons.myxa.data.model;

import softeng2.teamhortons.myxa.data.model.dao.UserDao;

public class User extends Model {
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMale;

    public User(String id, String email, String firstName, String lastName, int age, boolean isMale) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMale = isMale;
    }

    public User(String id, UserDao user) {
        super(id);
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.isMale = user.isMale();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public Object toDao() {
        return new UserDao(this.email, this.firstName, this.lastName, this.age, this.isMale);
    }
}
