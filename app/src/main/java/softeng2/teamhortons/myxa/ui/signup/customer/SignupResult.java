package softeng2.teamhortons.myxa.ui.signup.customer;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

import softeng2.teamhortons.myxa.ui.signup.customer.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 */
class SignupResult {
    @Nullable
    private FirebaseUser success;
    @Nullable
    private Integer error;

    SignupResult(@Nullable Integer error) {
        this.error = error;
    }

    SignupResult(@Nullable FirebaseUser success) {
        this.success = success;
    }

    @Nullable
    FirebaseUser getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
