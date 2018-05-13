package app.example.baking.bakingapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.model.Recipe;


/**
 * Created by Katy on 09.05.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private Recipe[] mRecipeData;
    public TextView mName;
    public TextView mServings;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipeItem);
    }


    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;

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
            Recipe recipeItem = mRecipeData[adapterPosition];
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
        String name = mRecipeData[position].getName();
        String servings = String.valueOf(mRecipeData[position].getServings());
        String image = mRecipeData[position].getImage();
        String steps = String.valueOf(mRecipeData[position].getSteps());

        mName.setText(name);
        mServings.setText("Servings: "+servings);

        Log.e(TAG, "name: " + name);
        Log.e(TAG, "image: " + image);
        Log.e(TAG, "steps: " + steps);

    }



    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.length;
    }


    public void setRecipeData(Recipe[] recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }

}