package softeng2.teamhortons.myxa.ui.signup.customer;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class SignupFormState {
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    @Nullable
    private Integer genderError;
    @Nullable
    private Integer ageError;

    private boolean isDataValid;

    SignupFormState(
            @Nullable Integer emailError,
            @Nullable Integer firstNameError,
            @Nullable Integer lastNameError,
            @Nullable Integer passwordError,
            @Nullable Integer confirmPasswordError,
            @Nullable Integer genderError,
            @Nullable Integer ageError) {
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.genderError = genderError;
        this.ageError = ageError;
        this.isDataValid = false;
    }

    SignupFormState(boolean isDataValid) {
        this.firstNameError = null;
        this.lastNameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.genderError = null;
        this.ageError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getFirstNameError() { return firstNameError; }

    @Nullable
    public Integer getLastNameError() { return lastNameError; }

    @Nullable
    public Integer getConfirmPasswordError() { return confirmPasswordError; }

    @Nullable
    public Integer getGenderError() { return genderError; }

    @Nullable
    public Integer getAgeError() { return ageError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
