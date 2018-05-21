package app.example.baking.bakingapp.ui.fragments;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.example.baking.bakingapp.utils.ApiClient;
import app.example.baking.bakingapp.utils.ApiInterface;
import app.example.baking.bakingapp.ui.activities.DetailsActivity;
import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.adapter.RecipeAdapter;

import app.example.baking.bakingapp.model.Recipe;
import app.example.baking.bakingapp.widget.BakingWidgetProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = RecipesFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipes;
    public static ApiInterface mApiInterface;

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
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerview_recipe);

        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this,mRecipes);
        mRecyclerView.setAdapter(mRecipeAdapter);

        loadRecipeData();

        return rootView;
    }


    private void loadRecipeData() {
        if (!isOnline()) return;

        Call<List<Recipe>> call = mApiInterface.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes = (ArrayList<Recipe>) response.body();
                Log.e(TAG, "loading " );
                Log.e(TAG, "loading "+ mRecipes );
                mRecipeAdapter.setRecipeData(mRecipes);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        getActivity().setTitle("Let's Bake");
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
        Log.e(TAG, "click ");


        Intent intentWidget = new Intent(getActivity(), BakingWidgetProvider.class);
        intentWidget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
        intentWidget.putExtra("recipeObject", recipeItem);
        getActivity().sendBroadcast(intentWidget);
    }


}
