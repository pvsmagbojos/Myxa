package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.generic.DeliveryType;
import softeng2.teamhortons.myxa.ui.menu.home.delivery.adapter.DeliveryListAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class DeliveryFragment extends Fragment {

    private DeliveryViewModel deliveryViewModel;
    private String TAG = "DeliveryFragment";

    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryViewModel = new ViewModelProvider(this,
                new DeliveryViewModelFactory()).get(DeliveryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deliveries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<RecyclerView> recyclerViews = initRecyclerViews(view);

        deliveryViewModel.getQueryResult().observe(getViewLifecycleOwner(), queryResult -> {
            if (queryResult.getError() != null) {
                Log.e(TAG, "FetchFromRemote Failed", queryResult.getError());
            }
            if (queryResult.getSuccess() != null) {
                TextView ongoingCategory = view.findViewById(R.id.textView_ongoing_category);
                final boolean[] hasOngoing = {false, false};
                deliveryViewModel.getPastOrdersList().observe(getViewLifecycleOwner(), orders -> {
                    TextView pastLabel = view.findViewById(R.id.textView_past_category);
                    if (orders.size() > 0) {
                        pastLabel.setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_PAST).setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_PAST).swapAdapter(
                                new DeliveryListAdapter(orders, DeliveryType.DELIVERY_PAST), false
                        );
                    } else {
                        pastLabel.setVisibility(GONE);
                        recyclerViews.get(DeliveryType.DELIVERY_PAST).setVisibility(GONE);
                    }
                });

                deliveryViewModel.getOngoingOrdersList().observe(getViewLifecycleOwner(), orders -> {
                    TextView ongoingLabel = view.findViewById(R.id.textView_ongoing_label);
                    hasOngoing[0] = orders.size() > 0;
                    if (hasOngoing[0]) {
                        ongoingLabel.setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_ONGOING).setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_ONGOING).swapAdapter(
                                new DeliveryListAdapter(orders, DeliveryType.DELIVERY_ONGOING), false
                        );
                    } else {
                        ongoingCategory.setVisibility((hasOngoing[1]) ? VISIBLE : GONE);
                        ongoingLabel.setVisibility(GONE);
                        recyclerViews.get(DeliveryType.DELIVERY_ONGOING).setVisibility(GONE);
                    }
                });

                deliveryViewModel.getScheduledOrdersList().observe(getViewLifecycleOwner(), orders -> {
                    TextView scheduledLabel = view.findViewById(R.id.textView_schedule_label);
                    hasOngoing[1] = orders.size() > 0;
                    if (hasOngoing[1]) {
                        scheduledLabel.setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_SCHEDULED).setVisibility(VISIBLE);
                        recyclerViews.get(DeliveryType.DELIVERY_SCHEDULED).swapAdapter(
                                new DeliveryListAdapter(orders, DeliveryType.DELIVERY_SCHEDULED), false
                        );
                    } else {
                        ongoingCategory.setVisibility((hasOngoing[0]) ? VISIBLE : GONE);
                        scheduledLabel.setVisibility(GONE);
                        recyclerViews.get(DeliveryType.DELIVERY_SCHEDULED).setVisibility(GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //deliveryViewModel.reload();
    }

    private ArrayList<RecyclerView> initRecyclerViews(View view) {
        ArrayList<RecyclerView> recyclerViews = new ArrayList<>();
        recyclerViews.add(view.findViewById(R.id.recyclerView_past_deliveries));
        recyclerViews.add(view.findViewById(R.id.recyclerView_ongoing_deliveries));
        recyclerViews.add(view.findViewById(R.id.recyclerView_scheduled_deliveries));

        for(RecyclerView recyclerView : recyclerViews) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(
                    new DeliveryListAdapter(new ArrayList<>(), DeliveryType.DELIVERY_GENERIC));
        }

        return recyclerViews;
    }
}
