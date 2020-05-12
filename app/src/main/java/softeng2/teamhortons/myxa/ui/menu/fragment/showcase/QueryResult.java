package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.CategoryItem;

class QueryResult {
    @Nullable
    private ArrayList<CategoryItem> success;
    @Nullable
    private Exception error;

    QueryResult(@Nullable Exception error) {
        this.error = error;
    }

    QueryResult(@Nullable ArrayList<CategoryItem> success) {
        this.success = success;
    }

    @Nullable
    ArrayList<CategoryItem> getSuccess() {
        return success;
    }

    @Nullable
    Exception getError() {
        return error;
    }
}
