package softeng2.teamhortons.myxa.ui.rider.orders.ui.riderorders;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Order;

class OrderResult {
    private ArrayList<Order> success;
    @Nullable
    private Exception error;

    OrderResult(@Nullable Exception error) {
        this.error = error;
    }

    OrderResult(ArrayList<Order> success) {
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
