package softeng2.teamhortons.myxa.ui.menu.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import softeng2.teamhortons.myxa.R;

public class HomeFragment extends Fragment {

    private String[] titles = new String[]{"For You", "Favorites", "Deliveries"};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(this);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager_home);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(homePagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout_home);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
    }
}
