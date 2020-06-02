package softeng2.teamhortons.myxa.ui.menu.home.cart.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Cart;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.model.Category;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter.RecipeListAdapter;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private ArrayList<CartItem> dataset;
    private OnItemClickListener mOnClick;

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
        void removeRecipe(CartItem cartItem);
        void plusQuantity(CartItem cartItem);
        void minusQuantity(CartItem cartItem);
    }

    public CartItemAdapter(ArrayList<CartItem> dataset, Context context) {
        Log.d("CartItemDataset", dataset.toString());
        this.dataset = dataset;

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
                //function call
                mOnClick.removeRecipe(dataset.get(pos));
            }
        });
        holder.btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function call
                mOnClick.plusQuantity(dataset.get(pos));
            }
        });
        holder.btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function call
                mOnClick.minusQuantity(dataset.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
