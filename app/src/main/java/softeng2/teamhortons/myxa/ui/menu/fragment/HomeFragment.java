package softeng2.teamhortons.myxa.ui.menu.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.fragment.showcase.ShowcaseFragment;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView.OnNavigationItemSelectedListener select = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager;
            switch (item.getItemId()) {
                    case R.id.nav_for_you:
                        ShowcaseFragment forYou = new ShowcaseFragment();
                        fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.homeFrame, forYou).commit();
                        return true;
                    case R.id.nav_your_fave:
                        YourFavoriteFragment yourFave = new YourFavoriteFragment();
                        fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.homeFrame, yourFave).commit();
                        return true;
                    case R.id.nav_deliveries:
                        DeliveriesFragment deliveries = new DeliveriesFragment();
                        fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.homeFrame, deliveries).commit();
                        return true;
                    default:
                        return false;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentManager fragmentManager;
        ShowcaseFragment forYou = new ShowcaseFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homeFrame, forYou).commit();

        BottomNavigationView navigate = (BottomNavigationView) v.findViewById(R.id.homeNavigationView);
        navigate.setOnNavigationItemSelectedListener(select);

        return v;
    }

}
