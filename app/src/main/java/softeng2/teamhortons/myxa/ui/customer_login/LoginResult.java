package  softeng2.teamhortons.myxa.ui.customer_login;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private FirebaseUser success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable FirebaseUser success) {
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
