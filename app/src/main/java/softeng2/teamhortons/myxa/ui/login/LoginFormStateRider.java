package softeng2.teamhortons.myxa.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormStateRider {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormStateRider(@Nullable Integer emailError, @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormStateRider(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getEmailErrorRider() {
        return emailError;
    }

    @Nullable
    Integer getPasswordErrorRider() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
