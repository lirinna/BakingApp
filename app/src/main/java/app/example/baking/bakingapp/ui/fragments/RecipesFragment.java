package app.example.baking.bakingapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.example.baking.bakingapp.ui.activities.DetailsActivity;
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

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerview_recipe);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);


        loadRecipeData();

        return rootView;
    }

    private void loadRecipeData() {
        if (!isOnline()) return;
        new RecipeLoader(mRecipeAdapter).execute();
        getActivity().setTitle("Let's Bake");

        Log.e(TAG, "loading " );
    }

    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(Recipe recipeItem) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("recipeObject", recipeItem);
        startActivity(intent);
        Log.e(TAG, "click " );
    }


}
