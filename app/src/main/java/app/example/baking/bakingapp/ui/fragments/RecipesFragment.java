package app.example.baking.bakingapp.ui.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.adapter.RecipeAdapter;
import app.example.baking.bakingapp.loaders.RecipeLoader;
import app.example.baking.bakingapp.model.Recipe;

public class RecipesFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler{

    private static final String TAG = RecipesFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        loadRecipeData();
    }

    private void loadRecipeData() {
        if (!isOnline()) return;
        new RecipeLoader(mRecipeAdapter).execute();
        setTitle("None");

        Log.e(TAG, "loading " );
    }

    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(Recipe recipeItem) {


        Log.e(TAG, "click " );
    }


}
