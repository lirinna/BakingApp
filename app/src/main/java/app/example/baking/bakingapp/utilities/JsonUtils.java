package app.example.baking.bakingapp.utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.example.baking.bakingapp.model.Ingredients;
import app.example.baking.bakingapp.model.Recipe;
import app.example.baking.bakingapp.model.Step;

/**
 * Created by Katy on 09.05.2018.
 */

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";

    private static final String INGREDIENTS_QUANTITY = "quantity";
    private static final String INGREDIENTS_MEASURE = "measure";
    private static final String INGREDIENTS_INGRDIENT = "ingredient";

    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEPS_DESCRIPTION = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";


    public static Recipe[] parseRecipesJson(String json) throws JSONException {

        JSONArray recipeDataArray = new JSONArray(json);
        Recipe[] recipeArray = new Recipe[recipeDataArray.length()];

        JSONObject jsonObject_result;
        for (int i = 0; i < recipeDataArray.length(); i++) {
            jsonObject_result = recipeDataArray.getJSONObject(i);

            Recipe recipe = new Recipe();
            recipe.setId(Integer.parseInt(jsonObject_result.optString(RECIPE_ID)));
            recipe.setName(jsonObject_result.optString(RECIPE_NAME));
            recipe.setServings(Integer.parseInt(jsonObject_result.optString(RECIPE_SERVINGS)));
            recipe.setImage(jsonObject_result.optString(RECIPE_IMAGE));

            // INGREDIENTS
            JSONArray ingredientsDataArray = jsonObject_result.getJSONArray(RECIPE_INGREDIENTS);
            ArrayList<Ingredients> ingredientsList = new ArrayList<>();
            JSONObject jsonObject_result_Ingredients;

            for (int j = 0; j < ingredientsDataArray.length(); j++) {
                jsonObject_result_Ingredients = ingredientsDataArray.getJSONObject(j);

                Ingredients ingredients = new Ingredients();
                ingredients.setIngredient(jsonObject_result_Ingredients.optString(INGREDIENTS_INGRDIENT));
                ingredients.setMeasure(jsonObject_result_Ingredients.optString(INGREDIENTS_MEASURE));
                ingredients.setQuantity(Float.parseFloat(jsonObject_result_Ingredients.optString(INGREDIENTS_QUANTITY)));
                ingredientsList.add(ingredients);
            }
            recipe.setIngredients(ingredientsList);

            // STEPS
            JSONArray stepsDataArray = jsonObject_result.getJSONArray(RECIPE_STEPS);
            ArrayList<Step> stepsList = new ArrayList<>();
            JSONObject jsonObject_result_Steps;

            for (int j = 0; j < stepsDataArray.length(); j++) {
                jsonObject_result_Steps = stepsDataArray.getJSONObject(j);

                Step steps = new Step();
                steps.setId(Integer.parseInt(jsonObject_result_Steps.optString(STEPS_ID)));
                steps.setDescription(jsonObject_result_Steps.optString(STEPS_DESCRIPTION));
                steps.setShortDescription(jsonObject_result_Steps.optString(STEPS_SHORT_DESCRIPTION));
                steps.setThumbnailURL(jsonObject_result_Steps.optString(STEPS_THUMBNAIL_URL));
                steps.setVideoURL(jsonObject_result_Steps.optString(STEPS_VIDEO_URL));

                stepsList.add(steps);
            }
            recipe.setSteps(stepsList);
            recipeArray[i] = recipe;
        }

        return recipeArray;
    }
}
