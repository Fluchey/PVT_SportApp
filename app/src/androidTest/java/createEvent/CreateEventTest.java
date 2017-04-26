package createEvent;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sportify.createEvent.activity.CreateEventActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sportapp.pvt_sportapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Maja on 2017-04-26.
 */
@RunWith(AndroidJUnit4.class)
public class CreateEventTest {

    /* Which class would you like the test to start in? */
    @Rule
    public ActivityTestRule<CreateEventActivity> createEventActivityActivityTestRule = new ActivityTestRule<CreateEventActivity>(CreateEventActivity.class);

    @Test
    public void createEventShouldFailEmpytName(){
        onView(withId(R.id.etEventDate)).perform(typeText("2017-01-01"));
        onView(withId(R.id.etEventStartTime)).perform(typeText("13:00"));
        onView(withId(R.id.etEventEndTime)).perform(typeText("16:00"));
        onView(withId(R.id.etEventType)).perform(typeText("Basket"));
        onView(withId(R.id.etEventPlace)).perform(typeText("Kista"));
        onView(withId(R.id.etEventDescription)).perform(typeText("TestEvent"));
        onView(withId(R.id.createEventButton)).perform(click());

        onView(withId(R.id.tvCreateEventMessage)).check(matches(withText(R.string.event_name_empty_error)));
    }

    //TODO: Ska vi ha denna check för tiderna också? Beror ju på hur det kommer läggas in i fortsättningen / om det ska formateras automatiskt
    @Test
    public void createEventWrongDateFormat(){
        onView(withId(R.id.etEventName)).perform(typeText("Testa datum"));
        onView(withId(R.id.etEventDate)).perform(typeText("170101"));
        onView(withId(R.id.etEventStartTime)).perform(typeText("13:00"));
        onView(withId(R.id.etEventEndTime)).perform(typeText("16:00"));
        onView(withId(R.id.etEventType)).perform(typeText("Basket"));
        onView(withId(R.id.etEventPlace)).perform(typeText("Kista"));
        onView(withId(R.id.etEventDescription)).perform(typeText("TestEvent"));
        onView(withId(R.id.createEventButton)).perform(click());

        onView(withId(R.id.tvCreateEventMessage)).check(matches(withText(R.string.event_date_wrongformat_error)));
    }
}
