package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.fragment.showcase.recyclerview.CategoryListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowcaseFragment extends Fragment {

    private ShowcaseViewModel showcaseViewModel;
    private String TAG = "ShowcaseFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowcaseFragment() {
        //ignore
    }

    public static ShowcaseFragment newInstance() {
        return new ShowcaseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showcaseViewModel = ViewModelProviders.of(this, new ShowcaseViewModelFactory())
                .get(ShowcaseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_showcase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView categoryListRecyclerView = view.findViewById(R.id.recyclerView_category);
        categoryListRecyclerView.setHasFixedSize(true);
        categoryListRecyclerView.setLayoutManager(new LinearLayoutManager(
                        this.getContext(), LinearLayoutManager.VERTICAL, false));

        showcaseViewModel.getQueryResult().observe(getViewLifecycleOwner(),
                new Observer<QueryResult>() {
            @Override
            public void onChanged(QueryResult queryResult) {
                if (queryResult.getError() != null) {
                    Log.e(TAG, "FetchFromRemote Failed", queryResult.getError());
                }
                if (queryResult.getSuccess() != null) {
                    categoryListRecyclerView.swapAdapter(
                            new CategoryListAdapter(queryResult.getSuccess()),false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showcaseViewModel.reload();
    }
}
