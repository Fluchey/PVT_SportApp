package register;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.register.activity.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.SecureRandom;

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
public class RegisterTest {
    /* Which class would you like the test to start in? */
    @Rule
    public ActivityTestRule<RegisterActivity> loginActivityActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void createAccountShouldPass(){
        SecureRandom random = new SecureRandom();

        /* Generates a string which should be kind of unique =)=) */
        String randomUsername = new BigInteger(130, random).toString(32);
        String randomMail = new BigInteger(130, random).toString(32);

        onView(withId(R.id.etRegisterUserName)).perform(typeText(randomUsername), closeSoftKeyboard());
        onView(withId(R.id.etRegisterMail)).perform(typeText(randomMail + "@espresso.com"), closeSoftKeyboard());
        onView(withId(R.id.etRegisterPassword)).perform(typeText("EspressoPassword"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
    }

    @Test
    public void createAccountShouldFailOnPassword(){
        onView(withId(R.id.etRegisterUserName)).perform(typeText("Espresso"));
        onView(withId(R.id.etRegisterMail)).perform(typeText("RandomMail@espresso.com"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.etRegisterPassword)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.password_empty_error))));
    }

    @Test
    public void createAccountShouldFailOnusername(){
        onView(withId(R.id.etRegisterUserName)).perform(typeText(""));
        onView(withId(R.id.etRegisterMail)).perform(typeText("RandomMail@espresso.com"), closeSoftKeyboard());
        onView(withId(R.id.etRegisterPassword)).perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.etRegisterUserName)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.username_empty_error))));
    }

    @Test
    public void createAccountShouldFailOnMailEmpty(){
        onView(withId(R.id.etRegisterUserName)).perform(typeText("EspressoName"));
        onView(withId(R.id.etRegisterMail)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etRegisterPassword)).perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.etRegisterMail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_Empty_error))));
    }

    @Test
    public void createAccountShouldFailOnMailWrongFormat(){
        onView(withId(R.id.etRegisterUserName)).perform(typeText("EspressoName"));
        onView(withId(R.id.etRegisterMail)).perform(typeText("wrongFormat@forgetDotCom"), closeSoftKeyboard());
        onView(withId(R.id.etRegisterPassword)).perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.etRegisterMail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_wrongFormat_error))));
    }
}
