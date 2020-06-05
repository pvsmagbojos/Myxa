package softeng2.teamhortons.myxa.ui.menu.home.cart;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Cart;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.model.Recipe;

public class CartQueryResult {
    @Nullable
    private ArrayList<CartItem> success;
    @Nullable
    private Exception error;

    CartQueryResult(@Nullable ArrayList<CartItem> success) {
        this.success = success;
    }

    CartQueryResult(@Nullable Exception error) {
        this.error = error;
    }

    @Nullable
    ArrayList<CartItem>  getSuccess() {
        return success;
    }

    @Nullable
    Exception getError() {
        return error;
    }
}
