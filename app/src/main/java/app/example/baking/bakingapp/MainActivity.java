package app.example.baking.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
