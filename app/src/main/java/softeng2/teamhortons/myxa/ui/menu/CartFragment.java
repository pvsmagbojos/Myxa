package softeng2.teamhortons.myxa.ui.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import softeng2.teamhortons.myxa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment
{

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}
