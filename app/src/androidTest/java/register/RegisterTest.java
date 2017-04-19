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
        String randomMail = new BigInteger(130, random).toString(32);

        onView(withId(R.id.etRegisterMail)).perform(typeText(randomMail + "@espresso.com"), closeSoftKeyboard());
        onView(withId(R.id.etRegisterFirstName)).perform(typeText("EspressoTestFirstName"));
        onView(withId(R.id.etRegisterLastName)).perform(typeText("EspressoTestLastName"));
        onView(withId(R.id.etRegisterPassword)).perform(typeText("EspressoPassword"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
    }
}
