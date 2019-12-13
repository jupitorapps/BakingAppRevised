package com.example.bakingappproject;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest1 {

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResources() {

        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {

            idlingResource = activity.getIdlingResources();
            IdlingRegistry.getInstance().register(idlingResource);

        });

    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTestDemo() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_receipenames),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container_for_fragments),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.item_image_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_receipenames),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.item_image_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_receipenames),
                                        1),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_item_receipe_name), withText("Brownies"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_receipenames),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Brownies")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tv_item_receipe_name), withText("Brownies"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_receipenames),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Brownies")));

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_receipenames),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());


        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.container_for_fragments),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));




    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    @After

    public void unregisterIdlingResources() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

}
