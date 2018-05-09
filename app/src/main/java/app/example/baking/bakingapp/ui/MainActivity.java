package app.example.baking.bakingapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.adapter.RecipeAdapter;
import app.example.baking.bakingapp.loaders.RecipeLoader;
import app.example.baking.bakingapp.model.Recipe;


// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler  {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
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



