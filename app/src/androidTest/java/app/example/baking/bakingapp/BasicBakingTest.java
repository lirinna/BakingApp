package app.example.baking.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.example.baking.bakingapp.ui.activities.MainActivity;
import app.example.baking.bakingapp.utils.OkHttpProvider;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

// Source: https://github.com/chiuki/espresso-samples/tree/master/idling-resource-okhttp
// Add annotation to specify AndroidJUnitRunner class as the default test runner
@RunWith(AndroidJUnit4.class)
public class BasicBakingTest {

    //Add the rule that provides functional testing of a single activity
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void name() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(
                "okhttp", OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withText("Brownies")).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void clickItem() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(
                "okhttp", OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
