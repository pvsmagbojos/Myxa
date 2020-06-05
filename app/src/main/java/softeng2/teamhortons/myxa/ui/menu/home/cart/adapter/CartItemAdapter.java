package softeng2.teamhortons.myxa.ui.menu.home.cart.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Cart;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.model.Category;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.ui.menu.home.cart.CartFragment;
import softeng2.teamhortons.myxa.ui.menu.home.cart.CartQueryResult;
import softeng2.teamhortons.myxa.ui.menu.home.cart.CartViewModel;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter.RecipeListAdapter;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private ArrayList<CartItem> dataset;
    private OnItemClickListener mOnClick;
    private CartFragment cartFragment;
    private CartViewModel cartViewModel;

    static class CartItemViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView imgUriPreview;
        TextView price;
        TextView quantity;

        Button btnRemove;
        Button btnPlusQuantity;
        Button btnMinusQuantity;


        public CartItemViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.textView_cart_item_name);
            imgUriPreview = v.findViewById(R.id.imageView_cart_item_image);
            price = v.findViewById(R.id.textView_cart_item_price);
            quantity = v.findViewById(R.id.textView_cart_item_qty);

            // buttons
            btnRemove = v.findViewById(R.id.button_remove_recipe);
            btnPlusQuantity = v.findViewById(R.id.button_qtyPlus);
            btnMinusQuantity = v.findViewById(R.id.button_qtyMinus);
        }
    }

    public interface OnItemClickListener{
        Task removeRecipe(DocumentReference recipeRef);
        Task plusQuantity(DocumentReference recipeRef);
        Task minusQuantity(DocumentReference recipeRef);
    }

    public CartItemAdapter(ArrayList<CartItem> dataset, Context context, CartFragment cf) {
        Log.d("CartItemDataset", dataset.toString());
        this.dataset = dataset;
        this.cartFragment = cf;
        Log.d("CartFragmentObject", cf.toString());
        this.cartViewModel = cf.getCartViewModel();

        try { this.mOnClick = ((OnItemClickListener) context); }
        catch (ClassCastException e) {
            Log.e("ERROR", "this is a message", e);
            throw new ClassCastException("Activity must implement OnItemClickListener");
        }
    }


    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_item, parent, false);

        return new CartItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Log.d("CartItemAdapter", String.valueOf(dataset.get(position).getName()));
        Log.d("CartItemAdapter", String.valueOf(dataset.get(position).getImgUriPreview()));
        Log.d("CartItemAdapter", String.valueOf(dataset.get(position).getPrice()));
        Log.d("CartItemAdapter", String.valueOf(dataset.get(position).getQuantity()));


        holder.name.setText(dataset.get(position).getName());
        holder.price.setText(String.valueOf(dataset.get(position).getPrice()));
        holder.quantity.setText(String.valueOf(dataset.get(position).getQuantity()));

        Picasso.get()
                .load(dataset.get(position).getImgUriPreview())
                .fit()
                .centerCrop()
                .into(holder.imgUriPreview);

        final int pos = position;
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("recipes")
                        .whereEqualTo("name", dataset.get(pos).getName())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("2CartItemAdapter", "remove, size: " + String.valueOf(queryDocumentSnapshots.size()));
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            Log.d("2CartItemAdapter", docSnap.getReference().getPath());
                            mOnClick.removeRecipe(docSnap.getReference()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    cartViewModel.setCartQueryResult(new MutableLiveData<CartQueryResult>());
                                    cartViewModel.reload();
                                    cartFragment.getFragmentManager().beginTransaction().detach(cartFragment).attach(cartFragment).commit();
                                }
                            });
                        }
                    }
                });
            }
        });
        holder.btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("recipes")
                        .whereEqualTo("name", dataset.get(pos).getName())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("2CartItemAdapter", "plus, size: " + String.valueOf(queryDocumentSnapshots.size()));
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            Log.d("2CartItemAdapter", docSnap.getReference().getPath());
                            mOnClick.plusQuantity(docSnap.getReference()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    cartViewModel.setCartQueryResult(new MutableLiveData<CartQueryResult>());
                                    cartViewModel.reload();
                                    cartFragment.getFragmentManager().beginTransaction().detach(cartFragment).attach(cartFragment).commit();
                                }
                            });
                        }
                    }
                });
            }
        });
        holder.btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("recipes")
                        .whereEqualTo("name", dataset.get(pos).getName())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("2CartItemAdapter", "minus, size: " + String.valueOf(queryDocumentSnapshots.size()));
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            Log.d("2CartItemAdapter", docSnap.getReference().getPath());
                            mOnClick.minusQuantity(docSnap.getReference()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    cartViewModel.setCartQueryResult(new MutableLiveData<CartQueryResult>());
                                    cartViewModel.reload();
                                    cartFragment.getFragmentManager().beginTransaction().detach(cartFragment).attach(cartFragment).commit();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
