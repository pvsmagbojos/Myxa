package softeng2.teamhortons.myxa.data.model;

import java.util.HashMap;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserDao {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMale;

    public UserDao() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserDao(String userId, String email, String firstName, String lastName, int age, boolean isMale) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMale = isMale;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
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

    public HashMap<String, Object> map() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("email", this.email);
        map.put("first", this.firstName);
        map.put("last", this.lastName);
        map.put("age", this.age);
        map.put("isMale", this.isMale);

        return map;
    }
}