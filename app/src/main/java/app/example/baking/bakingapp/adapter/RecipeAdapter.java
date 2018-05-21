package app.example.baking.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.model.Recipe;


/**
 * Created by Katy on 09.05.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private List<Recipe> mRecipeData;
    private TextView mName;
    private TextView mServings;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipeItem);
    }


    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, ArrayList<Recipe> mRecipes) {
        mClickHandler = clickHandler;
        mRecipeData = mRecipes;

    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public RecipeAdapterViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.tv_name);
            mServings = view.findViewById(R.id.tv_servings);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipeItem = mRecipeData.get(adapterPosition);
            mClickHandler.onClick(recipeItem);
        }
    }


    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new RecipeAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        String name = mRecipeData.get(position).getName();
        String servings = String.valueOf(mRecipeData.get(position).getServings());
        String image = mRecipeData.get(position).getImage();
        String steps = String.valueOf(mRecipeData.get(position).getSteps());

        mName.setText(name);
        mServings.setText("Servings: " + servings);

        Log.e(TAG, "name: " + name);
        Log.e(TAG, "image: " + image);
        Log.e(TAG, "steps: " + steps);

    }


    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.size();
    }


    public void setRecipeData(List<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }

}