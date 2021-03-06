package app.example.baking.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.model.Ingredients;
import app.example.baking.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BakingWidgetProvider.class.getSimpleName();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        if (intent.hasExtra("recipeObject")) {

            Recipe recipeObject = intent.getParcelableExtra("recipeObject");

            ArrayList<Ingredients> ingredients = recipeObject.getIngredients();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < ingredients.size(); i++) {
                Ingredients item = ingredients.get(i);
                String ingredientName = item.getIngredient();
                String ingredientUpper = ingredientName.substring(0,1).toUpperCase() + ingredientName.substring(1);

                String ingredientQuantity = String.valueOf(item.getQuantity());
                String ingredientMeasure = item.getMeasure();

                sb.append(ingredientUpper).append(" ").append(ingredientQuantity).append(" ").append(ingredientMeasure).append("\n");
            }

            Log.e(TAG, "listString " + sb);

            String name = recipeObject.getName();
            views.setTextViewText(R.id.appwidget_text, name);
            views.setTextViewText(R.id.appwidget_text_list_items, sb);

            updateWidget(context, views);
        }
    }

    public void updateWidget(Context context, RemoteViews remoteViews) {
        ComponentName widgetComponent = new ComponentName(context, BakingWidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetComponent, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

