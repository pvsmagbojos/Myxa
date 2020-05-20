package softeng2.teamhortons.myxa.ui.menu.home.cart;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import softeng2.teamhortons.myxa.data.model.Cart;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.repository.CartRepository;

public class CartViewModel extends ViewModel {
    private String TAG = "CartViewModel";

    private CartRepository cartRepository;

    private MutableLiveData<CartQueryResult> cartQueryResult = new MutableLiveData<>();

    CartViewModel(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    LiveData<CartQueryResult> getCartQueryResult() {
        reload();
        return cartQueryResult;
    }

    void reload() {
        Log.d("CartViewModel", "awit");
        if(this.cartQueryResult.getValue() != null) {
            if (cartQueryResult.getValue().getSuccess() != null) {
                Log.d("CartViewModel", "awit2");
                cartQueryResult.setValue(this.cartQueryResult.getValue());
            }

        }   else {
            Log.d("CartViewModel", "awit3");
                //final ArrayList<Task<DocumentSnapshot>> recipes = new ArrayList<>();
                // not really sure if i should use UserRepository or FirebaseAuth, the latter temporarily
                cartRepository.fetchCartListFromRemote(FirebaseAuth.getInstance().getUid())
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                //final Cart cart = new Cart();

                                // loops only once, query limit = 1
                                for(final DocumentSnapshot document : queryDocumentSnapshots) {
                                    HashMap<String, String> cart_recipes = (HashMap<String, String>) document.get("cart_recipes");

                                    //HashMap<String, Integer> cart_recipes = (HashMap<String, Integer>) document.get("cart_recipes");
                                    //cart.setCartRecipes(new ArrayList<CartItem>());
                                    final ArrayList<CartItem> cartItems = new ArrayList<>();

                                    for(HashMap.Entry<String, String> recipe : cart_recipes.entrySet()) {
                                        final int qty = Integer.valueOf(recipe.getValue());


                                        cartRepository.fetchRecipeFromRemote(recipe.getKey()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Log.d(TAG, documentSnapshot.get("name").toString());
                                                Log.d(TAG, documentSnapshot.get("imgUriPreview").toString());
                                                Log.d(TAG, String.valueOf(documentSnapshot.getDouble("price")));
                                                Log.d(TAG, String.valueOf(qty));


                                                CartItem cartItem = new CartItem(
                                                        documentSnapshot.get("name").toString(),
                                                        documentSnapshot.get("imgUriPreview").toString(),
                                                        documentSnapshot.getDouble("price"),
                                                        qty);

                                                Log.d("ItemTest", cartItem.getName());

                                                cartItems.add(cartItem);

                                                //cart.setCartRecipes(cartItems);
                                                cartQueryResult.setValue(new CartQueryResult(cartItems));
                                            }
                                        });

                                    }
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        cartQueryResult.setValue(new CartQueryResult(e));
                    }
                });

        }
    }
}
