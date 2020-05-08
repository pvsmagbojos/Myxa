package softeng2.teamhortons.myxa.ui.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.fragment.ScheduleFragment;
import softeng2.teamhortons.myxa.ui.menu.fragment.ShowcaseFragment;
import softeng2.teamhortons.myxa.ui.menu.fragment.HomeFragment;
import softeng2.teamhortons.myxa.ui.menu.fragment.CartFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenuActivity extends AppCompatActivity
{
    private BottomNavigationView mainBottomNavigation;
    private FrameLayout mainFrameLayout;

    private HomeFragment HomeFragment;
    private ScheduleFragment scheduleFragment;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mainFrameLayout = (FrameLayout)findViewById(R.id.frame_main_frameLayout);
        mainBottomNavigation = (BottomNavigationView)findViewById(R.id.nav_main_bottomNavigation);

        HomeFragment = new HomeFragment();
        scheduleFragment = new ScheduleFragment();
        cartFragment = new CartFragment();

        setFragment(HomeFragment);

        mainBottomNavigation.setSelectedItemId(R.id.nav_home);


        mainBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_diet_schedule:
                        setFragment(scheduleFragment);
                        return true;
                    case R.id.nav_home:
                        setFragment(HomeFragment);
                        return true;
                    case R.id.nav_your_cart:
                        setFragment(cartFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main_frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
