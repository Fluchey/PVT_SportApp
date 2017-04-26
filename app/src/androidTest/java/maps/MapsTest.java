package maps;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sportify.maps.activity.MapsActivity;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by fluchey on 2017-04-26.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapsTest {
    @Rule
    public ActivityTestRule<MapsActivity> mapsActivityActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class);

}
