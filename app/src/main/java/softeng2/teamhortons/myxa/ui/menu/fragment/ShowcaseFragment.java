package softeng2.teamhortons.myxa.ui.menu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ViewRecipeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowcaseFragment extends Fragment
{

    public ShowcaseFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_showcase, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
