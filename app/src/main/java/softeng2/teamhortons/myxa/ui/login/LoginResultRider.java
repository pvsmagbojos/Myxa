package softeng2.teamhortons.myxa.ui.login;

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
    FirebaseUser getSuccessRider() {
        return success;
    }

    @Nullable
    Integer getErrorRider() {
        return error;
    }
}
