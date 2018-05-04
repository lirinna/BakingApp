package app.example.baking.bakingapp.utilities;

import java.util.List;

import app.example.baking.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Katy on 02.05.2018.
 */

public class ApiInterface {

    @GET("59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
