package app.example.baking.bakingapp.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.ui.fragments.IngredientsFragment;
import app.example.baking.bakingapp.ui.fragments.RecipesFragment;
import app.example.baking.bakingapp.ui.fragments.StepsFragment;

// https://github.com/udacity/Android_Me/blob/TFragments.07-Solution-TwoPaneUI/app/src/main/java/com/example/android/android_me/ui/MainActivity.java

public class DetailsActivity extends AppCompatActivity {

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            StepsFragment stepsFragment = new StepsFragment();

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_ingredients, ingredientsFragment)
                    .add(R.id.container_steps, stepsFragment)
                    .commit();

        }


    }
}
