package app.example.baking.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.ui.fragments.RecipesFragment;


// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            RecipesFragment recipesFragment = new RecipesFragment();

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_recipes, recipesFragment)
                    .commit();

        }
    }
}



