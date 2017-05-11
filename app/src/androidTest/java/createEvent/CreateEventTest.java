//package createEvent;
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.sportify.createEvent.createEventPageOne.activity.CreateEventActivity;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import sportapp.pvt_sportapp.R;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
///**
// * Created by Maja on 2017-04-26.
// */
//@RunWith(AndroidJUnit4.class)
//public class CreateEventTest {
//
//    /* Which class would you like the test to start in? */
//    @Rule
//    public ActivityTestRule<CreateEventPreview> createEventActivityActivityTestRule = new ActivityTestRule<CreateEventPreview>(CreateEventPreview.class);
//
//    @Test
//    public void createEventShouldFailEmpytName(){
//        onView(withId(R.id.etEventDate)).perform(typeText("2017-01-01"), closeSoftKeyboard());
//        onView(withId(R.id.etEventStartTime)).perform(typeText("13:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventEndTime)).perform(typeText("16:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventType)).perform(typeText("Basket"), closeSoftKeyboard());
//        onView(withId(R.id.etEventPlace)).perform(typeText("Kista"), closeSoftKeyboard());
//        onView(withId(R.id.etEventDescription)).perform(typeText("TestEvent"), closeSoftKeyboard());
//        onView(withId(R.id.createEventButton)).perform(click());
//
//        onView(withId(R.id.etEventName)).check(matches(hasErrorText(createEventActivityActivityTestRule.getActivity().getString(R.string.event_name_empty_error))));
//    }
//
//    //TODO: Vet inte vad som egentligen ska checkas, använder detta test för att slippa skriva in allt själv.
//    @Test
//    public void createEventSucceded(){
//        onView(withId(R.id.etEventName)).perform(typeText("Testa espresso"), closeSoftKeyboard());
//        onView(withId(R.id.etEventDate)).perform(typeText("2017-01-01"), closeSoftKeyboard());
//        onView(withId(R.id.etEventStartTime)).perform(typeText("13:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventEndTime)).perform(typeText("16:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventType)).perform(typeText("Basket"), closeSoftKeyboard());
//        onView(withId(R.id.etEventPlace)).perform(typeText("Stadshagens IP"), closeSoftKeyboard());
//        onView(withId(R.id.etEventDescription)).perform(typeText("TestEvent"), closeSoftKeyboard());
//        onView(withId(R.id.createEventButton)).perform(click());
//
//    }
//
//    //TODO: Ska vi ha denna check för tiderna också? Beror ju på hur det kommer läggas in i fortsättningen / om det ska formateras automatiskt
//    @Test
//    public void createEventWrongDateFormat(){
//        onView(withId(R.id.etEventName)).perform(typeText("Testa datum"), closeSoftKeyboard());
//        onView(withId(R.id.etEventDate)).perform(typeText("170101"), closeSoftKeyboard());
//        onView(withId(R.id.etEventStartTime)).perform(typeText("13:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventEndTime)).perform(typeText("16:00"), closeSoftKeyboard());
//        onView(withId(R.id.etEventType)).perform(typeText("Basket"), closeSoftKeyboard());
//        onView(withId(R.id.etEventPlace)).perform(typeText("Kista"), closeSoftKeyboard());
//        onView(withId(R.id.etEventDescription)).perform(typeText("TestEvent"), closeSoftKeyboard());
//        onView(withId(R.id.createEventButton)).perform(click());
//
//        onView(withId(R.id.etEventDate)).check(matches(hasErrorText(createEventActivityActivityTestRule.getActivity().getString(R.string.event_date_wrongformat_error))));
//    }
//}
