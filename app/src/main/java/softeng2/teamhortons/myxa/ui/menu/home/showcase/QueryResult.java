package softeng2.teamhortons.myxa.ui.menu.home.showcase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Category;

class QueryResult {
    @Nullable
    private ArrayList<Category> success;
    @Nullable
    private Exception error;

    QueryResult(@Nullable Exception error) {
        this.error = error;
    }

    QueryResult(@Nullable ArrayList<Category> success) {
        this.success = success;
    }

    @Nullable
    ArrayList<Category> getSuccess() {
        return success;
    }

    @Nullable
    Exception getError() {
        return error;
    }
}
