package softeng2.teamhortons.myxa.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import softeng2.teamhortons.myxa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietScheduleFragment extends Fragment
{

    public DietScheduleFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet_schedule, container, false);
    }
}
