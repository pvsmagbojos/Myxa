package softeng2.teamhortons.myxa.ui.menu.home.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.ui.menu.home.cart.adapter.CartItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment
{
    private CartViewModel cartViewModel;
    private String TAG = "CartFragment";

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = ViewModelProviders.of(this, new CartViewModelFactory())
                .get(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final TextView totalTextView = view.findViewById(R.id.textView_cart_total_price);

        final RecyclerView cartListRecyclerView = view.findViewById(R.id.recyclerView_cart);
        cartListRecyclerView.setHasFixedSize(true);
        cartListRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false));

        cartListRecyclerView.setAdapter(new CartItemAdapter(new ArrayList<CartItem>(), this.getContext()));

        cartViewModel.getCartQueryResult().observe(getViewLifecycleOwner(),
                new Observer<CartQueryResult>() {
                    @Override
                    public void onChanged(CartQueryResult cartQueryResult) {
                        if(cartQueryResult.getError() != null) {
                            Log.e(TAG, "FetchFromRemote Failed", cartQueryResult.getError());
                        }

                        if(cartQueryResult.getSuccess() != null) {
                            cartListRecyclerView.setAdapter(
                                    new CartItemAdapter(cartQueryResult.getSuccess(), getContext()));

                            double total = 0;
                            for(CartItem c : cartQueryResult.getSuccess()) {
                                total += c.getPrice();
                            }
                            totalTextView.setText(String.valueOf(total));
                        }
                    }
                });
    }
}
