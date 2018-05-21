package app.example.baking.bakingapp.utils;

import java.util.List;

import app.example.baking.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Katy on 21.05.2018.
 */

public interface ApiInterface {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
