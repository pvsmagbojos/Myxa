package softeng2.teamhortons.myxa.ui.menu.fragment.showcase.recyclerview.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.CategoryItem;
import softeng2.teamhortons.myxa.ui.menu.fragment.showcase.recyclerview.recipe.RecipeListAdapter;

public class CategoryListAdapter extends
        RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {
    private ArrayList<CategoryItem> dataset;

    static class CategoryListViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recipeListRecyclerView;
        TextView categoryDescTextView;
        CategoryListViewHolder(View v) {
            super(v);
            this.categoryDescTextView = v.findViewById(R.id.textView_category_desc);
            this.recipeListRecyclerView = v.findViewById(R.id.recyclerView_recipe);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryListAdapter(ArrayList<CategoryItem> dataset) {
        this.dataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showcase_category, parent, false);
        CategoryListViewHolder vh = new CategoryListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        holder.categoryDescTextView.setText(dataset.get(position).getDesc());

        LinearLayoutManager llm = new LinearLayoutManager(holder.recipeListRecyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        llm.setInitialPrefetchItemCount(3);

        holder.recipeListRecyclerView.setAdapter(
                new RecipeListAdapter(dataset.get(position).getRecipeItems()));
    }

    @Override
    public int getItemCount() {
        return (dataset == null) ? 0 : dataset.size();
    }
}
