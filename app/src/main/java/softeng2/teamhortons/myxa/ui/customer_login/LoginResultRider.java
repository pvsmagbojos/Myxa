package softeng2.teamhortons.myxa.ui.customer_login;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResultRider {
    @Nullable
    private FirebaseUser success;
    @Nullable
    private Integer error;

    LoginResultRider(@Nullable Integer error) {
        this.error = error;
    }

    LoginResultRider(@Nullable FirebaseUser success) {
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
