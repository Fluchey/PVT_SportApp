package login;

import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.login.activity.LoginActivity;
import com.sportify.mainPage.activity.MainPageActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import sportapp.pvt_sportapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
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
    private Random random;
    private String randomMail;
    private String ra;

    @Rule
    public ActivityTestRule<MainPageActivity> loginActivityActivityTestRule = new ActivityTestRule<>(MainPageActivity.class);
    private LoginActivity loginActivity;

//    @Before
//    public void setLoginActivity(){
//        loginActivity = loginActivityActivityTestRule.getActivity();
//    }

//    @Test
//    public void loginNoPasswordShouldFail(){
//        onView(withId(R.id.etLoginEmail)).perform(typeText("emil@email.com"), closeSoftKeyboard());
//        onView(withId(R.id.etLoginPassword)).perform(typeText(""), closeSoftKeyboard());
//        onView(withId(R.id.loginButton)).perform(click());
//
//        onView(withId(R.id.etLoginPassword)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.password_empty_error))));
//    }
//
//    @Test
//    public void loginNoMailShouldFail(){
//        onView(withId(R.id.etLoginEmail)).perform(typeText(""), closeSoftKeyboard());
//        onView(withId(R.id.etLoginPassword)).perform(typeText("Password"), closeSoftKeyboard());
//        onView(withId(R.id.loginButton)).perform(click());
//
//        onView(withId(R.id.etLoginEmail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_Empty_error))));
//    }
//
//    @Test
//    public void loginmailWrongFormatShouldFail(){
//        onView(withId(R.id.etLoginEmail)).perform(typeText("wrongformat@nope"), closeSoftKeyboard());
//        onView(withId(R.id.etLoginPassword)).perform(typeText("Password"), closeSoftKeyboard());
//        onView(withId(R.id.loginButton)).perform(click());
//
//        onView(withId(R.id.etLoginEmail)).check(matches(hasErrorText(loginActivityActivityTestRule.getActivity().getString(R.string.email_wrongFormat_error))));
//    }

    @Test
    public void createAccountLoginCreateEvent(){
        SecureRandom random = new SecureRandom();

        /* Generates a string which should be kind of unique =)=) */
        randomMail = new BigInteger(130, random).toString(32);

        /**
         * First register screen
         */
        onView(withId(R.id.registerMPButton)).perform(click());
        onView(withId(R.id.etRegisterMail)).perform(typeText(randomMail + "@espresso.com"), closeSoftKeyboard() );
        onView(withId(R.id.etRegisterPassword)).perform(typeText(randomMail), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());


        /**
         * Enter Profile screen
         */
        onView(withId(R.id.etProfileNameHint)).perform(typeText("Espresso"), closeSoftKeyboard());
        onView(withId(R.id.etLastnameHint)).perform(typeText("EspressoLastName"), closeSoftKeyboard());
        onView(withId(R.id.etProfileBirthdayhint)).perform(typeText("1995-01-01"), closeSoftKeyboard());
        onView(withId(R.id.cbProfileFotboll)).perform(click());
        onView(withId(R.id.ibCheckboxProfileButton)).perform(click());

        /**
         * Login Screen
         */
        onView(withId(R.id.etLoginEmail)).perform(typeText(randomMail + "@espresso.com"));
        onView(withId(R.id.etLoginPassword)).perform(typeText(randomMail), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        /**
         * User Area
         */
        onView(withId(R.id.createEventButton)).perform(click());

        /**
         * Create event
         */

        onView(withId(R.id.etEventName)).perform(typeText(randomMail), closeSoftKeyboard());
        onView(withId(R.id.etEventPlace)).perform(typeText("Ab"), closeSoftKeyboard());
        onView(withText("Abrahamsbergs bollplan")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.etEventStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.etEventStartTime)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.etEventEndTime)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.etEventPrice)).perform(typeText("1337"), closeSoftKeyboard());
        onView(withId(R.id.etEventType)).perform(click());
        onView(withText("Simhall")).perform(click(), closeSoftKeyboard());
//        onView(withId(R.id.Next)).perform(click());
        onView(withText(R.string.event_realnext)).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();

        /**
         * User Area
         */
        onView(withId(R.id.goToMapButton)).perform(click());

        /**
         * Map
         */
        onView(withId(R.id.EventRadioButton)).perform(click());
        onView(withId(R.id.listButton)).perform(click());
        onView(withId(R.id.etMapsSearch)).perform(typeText(randomMail.substring(0, 10)), closeSoftKeyboard());
        onView(withText(randomMail)).perform(click());
    }

}
