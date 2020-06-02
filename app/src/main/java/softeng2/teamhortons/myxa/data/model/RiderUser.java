package softeng2.teamhortons.myxa.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@SuppressWarnings("unused")
public class RiderUser extends Model {

    private String email;
    private String firstName;
    private String lastName;
    private String plate;
    private String license;
    private int age;
    private boolean isMale;

    public RiderUser() {
        // Default constructor required for calls to DataSnapshot.toObject(User.class)
    }

    public RiderUser(String email, String firstName, String lastName, String plate, String license, int age, boolean isMale) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.plate = plate;
        this.license = license;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String lastName) {
        this.license = license;
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
