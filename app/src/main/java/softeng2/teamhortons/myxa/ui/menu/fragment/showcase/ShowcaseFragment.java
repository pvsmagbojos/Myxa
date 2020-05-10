package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.fragment.HomeFragment;
import softeng2.teamhortons.myxa.ui.menu.fragment.showcase.recyclerview.category.CategoryListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowcaseFragment extends Fragment {

    private ShowcaseViewModel showcaseViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowcaseFragment() {
    }

    public static ShowcaseFragment newInstance() {
        return new ShowcaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_showcase, container, false);

        RecyclerView categoryListRecyclerView = v.findViewById(R.id.recyclerView_category);
        categoryListRecyclerView.setHasFixedSize(true);
        categoryListRecyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        //TODO: Set adapter
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showcaseViewModel = ViewModelProviders.of(this).get(ShowcaseViewModel.class);
        // TODO: Use the ViewModel
    }

}
