package softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter;

import android.content.Context;
import android.util.Log;
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
    private OnItemClickListener mOnClick;

    static class RecipeListViewHolder extends RecyclerView.ViewHolder {
        ImageView previewImageView;
        RecipeListViewHolder(View v) {
            super(v);
            previewImageView = v.findViewById(R.id.imageView_preview);
        }
    }

    public interface OnItemClickListener{
       void viewModal(Recipe recipe);
    }

    RecipeListAdapter(ArrayList<Recipe> dataset, Context context) {
        Log.d("RecipeListDataSet", dataset.toString());
        this.dataset = dataset;

        try { this.mOnClick = ((OnItemClickListener) context); }
        catch (ClassCastException e) {
            Log.e("ERROR", "this is a message", e);
            throw new ClassCastException("Activity must implement OnItemClickListerner.");
        }
    }

    @Override
    @NonNull
    public RecipeListAdapter.RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showcase_recipe, parent, false);

        return new RecipeListViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipeListAdapter.RecipeListViewHolder holder, int position) {
        Picasso.get()
                .load(dataset.get(position).getImgUriPreview())
                .fit()
                .centerCrop()
                .into(holder.previewImageView);

        final int pos = position;
        holder.previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function call
                mOnClick.viewModal(dataset.get(pos));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
