package app.example.baking.bakingapp;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import app.example.baking.bakingapp.utilities.ApiClient;
import app.example.baking.bakingapp.utilities.ApiInterface;

// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);




    }
}
