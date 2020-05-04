package softeng2.teamhortons.myxa.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import softeng2.teamhortons.myxa.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity
{
    private BottomNavigationView mainBottomNavigation;
    private FrameLayout mainFrameLayout;

    private HomeFragment homeFragment;
    private DietScheduleFragment dietScheduleFragment;
    private YourCartFragment yourCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainFrameLayout = (FrameLayout)findViewById(R.id.frame_main_frameLayout);
        mainBottomNavigation = (BottomNavigationView)findViewById(R.id.nav_main_bottomNavigation);

        homeFragment = new HomeFragment();
        dietScheduleFragment = new DietScheduleFragment();
        yourCartFragment = new YourCartFragment();

        setFragment(homeFragment);

        mainBottomNavigation.setSelectedItemId(R.id.nav_home);

        mainBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_diet_schedule:
                        setFragment(dietScheduleFragment);
                        return true;
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_your_cart:
                        setFragment(yourCartFragment);
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
