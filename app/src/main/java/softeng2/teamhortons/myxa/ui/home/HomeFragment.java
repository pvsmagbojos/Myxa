package softeng2.teamhortons.myxa.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ViewRecipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{

    public HomeFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button viewRecipe = (Button)getView().findViewById(R.id.button11);

        viewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getContext(), ViewRecipe.class),
                        ViewRecipe.REQUEST_CODE);
            }
        });
    }
}
