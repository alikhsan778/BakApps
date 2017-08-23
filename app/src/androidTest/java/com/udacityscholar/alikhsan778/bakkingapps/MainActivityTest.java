package com.udacityscholar.alikhsan778.bakkingapps;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * alikhsan778
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setIdlingResource() {
        idlingResource = mainActivityIntentsTestRule.getActivity().countingIdlingResource;
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void testLaunchDetailActivity(){
        intending(allOf(
                hasComponent(hasShortClassName(".DetailActivity"))));

    }
    @Test
    public void testDatasRecipe(){
        onView(withId(R.id.rv_recipe_list)).check(matches(isDisplayed()));

    }

}
