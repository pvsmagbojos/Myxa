package softeng2.teamhortons.myxa.data.model.dao;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserDao {

    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMale;

    @SuppressWarnings("unused")
    public UserDao() {
        // Default constructor required for calls to DataSnapshot.getValue(UserDao.class)
    }

    public UserDao(String email, String firstName, String lastName, int age, boolean isMale) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMale = isMale;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }
}