package app.example.baking.bakingapp.loaders;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import app.example.baking.bakingapp.adapter.RecipeAdapter;
import app.example.baking.bakingapp.model.Recipe;
import app.example.baking.bakingapp.utilities.JsonUtils;
import app.example.baking.bakingapp.utilities.NetworkUtils;

/**
 * Created by Katy on 09.05.2018.
 */

public class RecipeLoader extends AsyncTask<String, Void, Recipe[]> {

    private RecipeAdapter mRecipeAdapter;
    private Recipe[] mRecipe = null;


    public RecipeLoader(RecipeAdapter adapter) {
        this.mRecipeAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Recipe[] doInBackground(String... params) {

        URL recipeUrl = NetworkUtils.buildUrl();

        try {
            String jsonRecipeResponse = NetworkUtils
                    .getResponseFromHttpUrl(recipeUrl);

            mRecipe = JsonUtils.parseRecipesJson(jsonRecipeResponse);
            return mRecipe;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Recipe[] recipeData) {
        if (recipeData != null) {
            mRecipeAdapter.setRecipeData(recipeData);
        }
    }
}