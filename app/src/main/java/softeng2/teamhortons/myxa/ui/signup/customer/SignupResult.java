package softeng2.teamhortons.myxa.ui.signup.customer;

import androidx.annotation.Nullable;

import softeng2.teamhortons.myxa.ui.signup.customer.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 */
class SignupResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    SignupResult(@Nullable Integer error) {
        this.error = error;
    }

    SignupResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
