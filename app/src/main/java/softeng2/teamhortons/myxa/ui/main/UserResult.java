package softeng2.teamhortons.myxa.ui.main;

import androidx.annotation.Nullable;

import softeng2.teamhortons.myxa.data.model.User;

class UserResult {
    @Nullable
    private User success;
    @Nullable
    private Exception error;

    UserResult(@Nullable Exception error) {
        this.error = error;
    }

    UserResult(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    User getSuccess() {
        return success;
    }

    @Nullable
    Exception getError() {
        return error;
    }
}
