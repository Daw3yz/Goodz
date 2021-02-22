package com.example.goodz;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UITest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void login(){
        onView(withId(R.id.emailEditText)).perform(typeText("david@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("12345678"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
    }

}
