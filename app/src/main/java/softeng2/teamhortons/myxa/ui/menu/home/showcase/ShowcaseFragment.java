package softeng2.teamhortons.myxa.ui.menu.home.showcase;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter.CategoryListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowcaseFragment extends Fragment{

    private ShowcaseViewModel showcaseViewModel;
    private String TAG = "ShowcaseFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowcaseFragment() {
        //ignore
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showcaseViewModel = new ViewModelProvider(this,
                new ShowcaseViewModelFactory()).get(ShowcaseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_showcase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView categoryListRecyclerView = view.findViewById(R.id.recyclerView_categories);
        categoryListRecyclerView.setHasFixedSize(true);
        categoryListRecyclerView.setLayoutManager(new LinearLayoutManager(
                        this.getContext(), LinearLayoutManager.VERTICAL, false));
        categoryListRecyclerView.setAdapter(new CategoryListAdapter(new ArrayList<>(), this.getContext()));

        showcaseViewModel.getQueryResult().observe(getViewLifecycleOwner(),
                queryResult -> {
                    if (queryResult.getError() != null) {
                        Log.e(TAG, "FetchFromRemote Failed", queryResult.getError());
                    }
                    if (queryResult.getSuccess() != null) {
                        categoryListRecyclerView.swapAdapter(
                                new CategoryListAdapter(queryResult.getSuccess(),getContext()),false);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        showcaseViewModel.reload();
    }
}
