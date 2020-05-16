package softeng2.teamhortons.myxa.ui.menu.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import softeng2.teamhortons.myxa.ui.menu.ScheduleFragment;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.ShowcaseFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    HomePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("pageradapter", String.valueOf(position));
        switch(position) {
            case 0:
                return new ShowcaseFragment();
            case 1:
                return new YourFavoriteFragment();
            case 2:
                return new DeliveriesFragment();
        }
        return new ScheduleFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
