package softeng2.teamhortons.myxa.ui.menu.fragment.showcase.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
    private ArrayList<Recipe> dataset;

    static class RecipeListViewHolder extends RecyclerView.ViewHolder {
        ImageView previewImageView;
        RecipeListViewHolder(ImageView v) {
            super(v);
            previewImageView = v;
        }
    }

    RecipeListAdapter(ArrayList<Recipe> dataset) {
        this.dataset = dataset;
    }

    @Override
    @NonNull
    public RecipeListAdapter.RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showcase_recipe, parent, false);

        ImageView previewImageView = view.findViewById(R.id.imageView_preview);

        return new RecipeListViewHolder(previewImageView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipeListAdapter.RecipeListViewHolder holder, int position) {
        Picasso.get()
                .load(dataset.get(position).getImgUriPreview())
                .fit()
                .centerCrop()
                .into(holder.previewImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
