package softeng2.teamhortons.myxa.ui.menu.home.delivery.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Order;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.DeliveryListViewHolder> {

    private ArrayList<Order> dataset;
    private int type;

    static class DeliveryListViewHolder extends RecyclerView.ViewHolder {
        TextView orderDescTextView;
        TextView orderStatusTextView;
        TextView orderPriceTextView;

        DeliveryListViewHolder(View v) {
            super(v);
            this.orderDescTextView = v.findViewById(R.id.textView_order_desc);
            this.orderStatusTextView = v.findViewById(R.id.textView_order_status);
            this.orderPriceTextView = v.findViewById(R.id.textView_order_price);
        }
    }

    public DeliveryListAdapter(ArrayList<Order> dataset, int type) {
        this.dataset = dataset;
        this.type = type;
    }

    @NonNull
    @Override
    public DeliveryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new DeliveryListViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DeliveryListViewHolder holder, int position) {
        //TODO: Add method to parse different order types for reusability
        //TODO: Add method to parse timestamps into status
        holder.orderDescTextView.setText(dataset.get(position).getDatePosted().toString());
        if(dataset.get(position).getDateCompleted() == null) {
            holder.orderStatusTextView.setText("null");
        } else {
            holder.orderStatusTextView.setText(dataset.get(position).getDateCompleted().toString());
        }

        holder.orderPriceTextView.setText(Double.toString(dataset.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
