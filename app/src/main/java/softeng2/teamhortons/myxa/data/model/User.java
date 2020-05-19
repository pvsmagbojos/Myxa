package softeng2.teamhortons.myxa.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@SuppressWarnings("unused")
public class User extends Model {

    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMale;

    public User() {
        // Default constructor required for calls to DataSnapshot.toObject(User.class)
    }

    public User(String email, String firstName, String lastName, int age, boolean isMale) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMale = isMale;
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
}
