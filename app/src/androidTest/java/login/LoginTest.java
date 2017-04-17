package login;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.SecureRandom;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by fluchey on 2017-04-17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    public static final String usernameShouldWork = "EspressoTest";
    public static final String passwordShouldWork = "PasswordTrue";


    public static final String usernameShouldNotWork = "EspressoUsernameFail";
    public static final String passwordShouldNotWork = "PasswordFail";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

}
