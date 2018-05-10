package app.example.baking.bakingapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.example.baking.bakingapp.R;

// https://github.com/udacity/Android_Me/blob/TFragments.07-Solution-TwoPaneUI/app/src/main/java/com/example/android/android_me/ui/MainActivity.java

public class DetailsActivity extends AppCompatActivity {

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);




    }
}
