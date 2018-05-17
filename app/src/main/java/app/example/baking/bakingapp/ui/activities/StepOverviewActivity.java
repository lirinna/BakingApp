package app.example.baking.bakingapp.ui.activities;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.ui.fragments.StepOverviewFragment;

public class StepOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_overview);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            StepOverviewFragment stepsFragment = new StepOverviewFragment();

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_step_overview, stepsFragment)
                    .commit();

        }
    }

   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }
}
