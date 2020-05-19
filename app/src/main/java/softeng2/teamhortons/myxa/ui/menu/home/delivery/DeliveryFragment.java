package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import softeng2.teamhortons.myxa.R;


public class DeliveryFragment extends Fragment {

    private DeliveryViewModel deliveryViewModel;

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
}
