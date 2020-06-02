package softeng2.teamhortons.myxa.ui.signup.rider;

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
    @Nullable
    private Integer plateError;
    @Nullable
    private Integer licenseError;

    private boolean isDataValid;

    SignupFormState(
            @Nullable Integer firstNameError,
            @Nullable Integer lastNameError,
            @Nullable Integer genderError,
            @Nullable Integer ageError,
            @Nullable Integer plateError,
            @Nullable Integer licenseError,
            @Nullable Integer emailError,
            @Nullable Integer passwordError,
            @Nullable Integer confirmPasswordError) {
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.genderError = genderError;
        this.ageError = ageError;
        this.plateError = plateError;
        this.licenseError = licenseError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
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
    Integer getFirstNameError() { return firstNameError; }

    @Nullable
    Integer getLastNameError() { return lastNameError; }

    @Nullable
    Integer getConfirmPasswordError() { return confirmPasswordError; }

    @Nullable
    Integer getGenderError() { return genderError; }

    @Nullable
    Integer getAgeError() { return ageError; }

    @Nullable
    Integer getPlateError() { return plateError; }

    @Nullable
    Integer getLicenseError() { return licenseError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
