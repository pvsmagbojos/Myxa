package softeng2.teamhortons.myxa.ui.menu.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import softeng2.teamhortons.myxa.ui.menu.home.delivery.DeliveryFragment;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.ShowcaseFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    HomePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new ShowcaseFragment();
            case 1:
                return new YourFavoriteFragment();
            case 2:
                return new DeliveryFragment();
        }
        return new ShowcaseFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
