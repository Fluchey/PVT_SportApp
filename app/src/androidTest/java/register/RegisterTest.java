package register;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.login.LoginActivity;
import com.sportify.register.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.SecureRandom;

import sportapp.pvt_sportapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
    @Rule
    public ActivityTestRule<RegisterActivity> loginActivityActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void wrongFormatusername(){
        onView(withId(R.id.etRegisterUsername)).perform(typeText("EspressoTest"));
        onView(withId(R.id.etRegisterUsername)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.tvSignUpMessage)).check(matches(withText("Please enter data in all fields")));
    }

//    @Test
//    public void createAccountShouldPass(){
//        SecureRandom random = new SecureRandom();
//
//        /* Generates a string which should be kind of unique =)=) */
//        String username = new BigInteger(130, random).toString(32);
//
//        onView(withId(R.id.etRegisterUsername)).perform(typeText(username));
//        onView(withId(R.id.etRegisterPassword)).perform(typeText("EspressoPassword"));
//        onView(withId(R.id.etRegisterFirstName)).perform(typeText("EspressoTestFirstName"));
//        onView(withId(R.id.etRegisterLastName)).perform(typeText("EspressoTestLastName"));
//        onView(withId(R.id.etRegisterPhoneNumber)).perform(typeText("123456789"));
//        onView(withId(R.id.etRegisterMail)).perform(typeText("EspressoTest@espresso.com"));
//        //onView(withId(R.id.registerButton)).perform(click());
//    }
}
