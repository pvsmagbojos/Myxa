package softeng2.teamhortons.myxa.ui.menu.home.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

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

    public CartViewModel getCartViewModel() {
        return this.cartViewModel;
    }

    final ArrayList<Double> total = new ArrayList<>();

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
        Log.d(TAG, "inflated");
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final TextView totalTextView = view.findViewById(R.id.textView_cart_total_price);
        final Button btnCheckout = view.findViewById(R.id.button_checkout);

        final RecyclerView cartListRecyclerView = view.findViewById(R.id.recyclerView_cart);
        cartListRecyclerView.setHasFixedSize(true);
        cartListRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false));

        cartListRecyclerView.setAdapter(new CartItemAdapter(new ArrayList<CartItem>(), this.getContext(), this));

        cartViewModel.getCartQueryResult().observe(getViewLifecycleOwner(),
                new Observer<CartQueryResult>() {
                    @Override
                    public void onChanged(CartQueryResult cartQueryResult) {
                        if (cartQueryResult.getError() != null) {
                            Log.e(TAG, "FetchFromRemote Failed", cartQueryResult.getError());
                        }

                        if (cartQueryResult.getSuccess() != null) {
                            cartListRecyclerView.setAdapter(
                                    new CartItemAdapter(cartQueryResult.getSuccess(), getContext(), CartFragment.this));

                            double tempTotal = 0;
                            for (CartItem c : cartQueryResult.getSuccess()) {
                                tempTotal += (c.getPrice() * c.getQuantity());
                            }
                            total.add(tempTotal);
                            totalTextView.setText(total.get(total.size()-1).toString());
                            btnCheckout.setEnabled(true);
                        }
                    }
                });



        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog2 = new ProgressDialog(CartFragment.this.getContext());
                dialog2.setMessage("Checking out...");
                dialog2.show();

                DocumentReference docRef = FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getUid());

                DocumentReference riderDocRef = FirebaseFirestore.getInstance().collection("riders")
                        .document("johndoe1990");

                HashMap<String, Object> orderData = new HashMap<>();
                orderData.put("dateCompleted", null);
                orderData.put("datePosted", FieldValue.serverTimestamp());
                orderData.put("riderRef", (DocumentReference) riderDocRef);
                orderData.put("userRef", docRef);
                orderData.put("totalPrice", total.get(total.size()-1));

                FirebaseFirestore.getInstance().collection("orders")
                        .add(orderData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        final DocumentReference orderDocRef = task.getResult();
                        // put recipe in orders > cart

                        // delete recipe in cart
                        FirebaseFirestore.getInstance().collection("users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("cart")
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Log.d("CartFragment", queryDocumentSnapshots.getDocuments().toString());
                                for(DocumentSnapshot docSnap3 : queryDocumentSnapshots.getDocuments()) {
                                    final DocumentReference deleteRecipe = docSnap3.getReference();
                                    HashMap<String, Object> data = new HashMap<>();
                                    data.put("qty", docSnap3.getString("qty"));
                                    data.put("recipeRef", docSnap3.get("recipe_id"));

                                    orderDocRef.collection("cart")
                                            .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            deleteRecipe.delete();

                                            dialog2.hide();

                                            cartViewModel.setCartQueryResult(new MutableLiveData<CartQueryResult>());
                                            cartViewModel.reload();
                                            getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });

//        cartViewModel.setCartQueryResult(new MutableLiveData<CartQueryResult>());
//        cartViewModel.reload();
//        getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
    }
}
