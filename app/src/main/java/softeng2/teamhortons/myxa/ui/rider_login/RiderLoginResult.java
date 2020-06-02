package softeng2.teamhortons.myxa.ui.rider_login;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Authentication result : success (user details) or error message.
 */
class RiderLoginResult {
    @Nullable
    private FirebaseUser success;
    @Nullable
    private Integer error;

    RiderLoginResult(@Nullable Integer error) {
        this.error = error;
    }

    RiderLoginResult(@Nullable FirebaseUser success) {
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
