package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.home.delivery.adapter.DeliveryListAdapter;


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
        //TODO: Turn into arraylist of recyclerviews
        RecyclerView scheduledDeliveryListRecyclerView = view.findViewById(R.id.recyclerView_scheduled_deliveries);
        scheduledDeliveryListRecyclerView.setHasFixedSize(true);
        scheduledDeliveryListRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false));
        scheduledDeliveryListRecyclerView.setAdapter(new DeliveryListAdapter(new ArrayList<>()));

        RecyclerView pastDeliveryListRecyclerView = view.findViewById(R.id.recyclerView_past_deliveries);
        pastDeliveryListRecyclerView.setHasFixedSize(true);
        pastDeliveryListRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false));
        pastDeliveryListRecyclerView.setAdapter(new DeliveryListAdapter(new ArrayList<>()));

        RecyclerView ongoingDeliveryListRecyclerView = view.findViewById(R.id.recyclerView_ongoing_deliveries);
        ongoingDeliveryListRecyclerView.setHasFixedSize(true);
        ongoingDeliveryListRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false));
        ongoingDeliveryListRecyclerView.setAdapter(new DeliveryListAdapter(new ArrayList<>()));

        deliveryViewModel.getQueryResult().observe(getViewLifecycleOwner(),
                queryResult -> {
                    if (queryResult.getError() != null) {
                        Log.e(TAG, "FetchFromRemote Failed", queryResult.getError());
                    }
                    if (queryResult.getSuccess() != null) {
                        //TODO: process queryResults to split between recyclerviews
                    }

                });
    }

    @Override
    public void onResume() {
        super.onResume();
        deliveryViewModel.reload();
    }
}
