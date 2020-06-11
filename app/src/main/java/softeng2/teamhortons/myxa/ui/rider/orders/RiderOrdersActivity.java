package softeng2.teamhortons.myxa.ui.rider.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Order;
import softeng2.teamhortons.myxa.generic.DeliveryType;
import softeng2.teamhortons.myxa.ui.menu.home.delivery.adapter.DeliveryListAdapter;
import softeng2.teamhortons.myxa.ui.rider.orders.ui.riderorders.RiderOrdersViewModel;
import softeng2.teamhortons.myxa.ui.rider.orders.ui.riderorders.RiderOrdersViewModelFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RiderOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_orders_activity);

        RiderOrdersViewModel ordersViewModel = new ViewModelProvider(this,
                new RiderOrdersViewModelFactory()).get(RiderOrdersViewModel.class);

        RecyclerView past = findViewById(R.id.recyclerView_past_deliveries);
        RecyclerView onGoing = findViewById(R.id.recyclerView_ongoing_deliveries);

        past.setHasFixedSize(true);
        past.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        past.setAdapter(
                new DeliveryListAdapter(new ArrayList<>(), DeliveryType.DELIVERY_PAST));

        onGoing.setHasFixedSize(true);
        onGoing.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        onGoing.setAdapter(
                new DeliveryListAdapter(new ArrayList<>(), DeliveryType.DELIVERY_ONGOING));

        ordersViewModel.getOngoingOrdersList().observe(this, orders -> {
            TextView ongoingLabel = findViewById(R.id.textView_ongoing_category);
            if (orders.size() > 0) {
                ongoingLabel.setVisibility(VISIBLE);
                onGoing.setVisibility(VISIBLE);
                onGoing.swapAdapter(
                        new DeliveryListAdapter(orders, DeliveryType.DELIVERY_ONGOING), false
                );
            } else {
                ongoingLabel.setVisibility(GONE);
                onGoing.setVisibility(GONE);
            }
        });

        ordersViewModel.getPastOrdersList().observe(this, orders -> {
            TextView pastLabel = findViewById(R.id.textView_past_category);
            if (orders.size() > 0) {
                pastLabel.setVisibility(VISIBLE);
                past.setVisibility(VISIBLE);
                past.swapAdapter(
                        new DeliveryListAdapter(orders, DeliveryType.DELIVERY_PAST), false
                );
            } else {
                pastLabel.setVisibility(GONE);
                past.setVisibility(GONE);
            }
        });

    }


}
