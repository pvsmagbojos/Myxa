package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Order;

class QueryResult {
    @Nullable
    private ArrayList<Order> success;
    @Nullable
    private Exception error;

    QueryResult(@Nullable Exception error) {
        this.error = error;
    }

    QueryResult(@Nullable ArrayList<Order> success) {
        this.success = success;
    }

    @Nullable
    ArrayList<Order> getSuccess() {
        return success;
    }

    @Nullable
    Exception getError() {
        return error;
    }
}
