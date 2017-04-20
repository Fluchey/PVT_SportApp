package login;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.login.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sportapp.pvt_sportapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by fluchey on 2017-04-17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
//    public static final String usernameShouldWork = "EspressoTest";
//    public static final String passwordShouldWork = "PasswordTrue";
//
//
//    public static final String usernameShouldNotWork = "EspressoUsernameFail";
//    public static final String passwordShouldNotWork = "PasswordFail";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginNoPasswordShouldFail(){
        onView(withId(R.id.etLoginEmail)).perform(typeText("emil@email.com"), closeSoftKeyboard());
        onView(withId(R.id.etLoginPassword)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.etLoginPassword)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.password_empty_error))));
    }

    @Test
    public void loginNoMailShouldFail(){
        onView(withId(R.id.etLoginEmail)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etLoginPassword)).perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.etLoginEmail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_Empty_error))));
    }

    @Test
    public void loginmailWrongFormatShouldFail(){
        onView(withId(R.id.etLoginEmail)).perform(typeText("wrongformat@nope"), closeSoftKeyboard());
        onView(withId(R.id.etLoginPassword)).perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.etLoginEmail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_wrongFormat_error))));
    }

}
