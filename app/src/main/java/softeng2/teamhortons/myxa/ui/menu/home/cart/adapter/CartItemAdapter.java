package softeng2.teamhortons.myxa.ui.menu.home.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter.RecipeListAdapter;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private ArrayList<CartItem> dataset;
    private RecipeListAdapter.OnItemClickListener mOnClick;

    static class CartItemViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView imgUriPreview;
        TextView price;
        EditText quantity;

        public CartItemViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.textView_cart_item_name);
            imgUriPreview = v.findViewById(R.id.imageView_cart_item_image);
            price = v.findViewById(R.id.textView_cart_item_price);
            quantity = v.findViewById(R.id.editText_cart_item_qty);
        }
    }



    public CartItemAdapter(ArrayList<CartItem> dataset, Context context) {
        Log.d("CartItemDataset", dataset.toString());
        this.dataset = dataset;

        try { this.mOnClick = ((RecipeListAdapter.OnItemClickListener) context); }
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
        //holder.quantity.setText(dataset.get(position).getQuantity(), TextView.BufferType.NORMAL);
        Picasso.get()
                .load(dataset.get(position).getImgUriPreview())
                .fit()
                .centerCrop()
                .into(holder.imgUriPreview);

        final int pos = position;
//        holder.imgUriPreview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //function call
//                mOnClick.viewModal(dataset.get(pos));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
