import com.sportify.register.presenter.RegisterPresenterImpl;
import com.sportify.register.request.RegisterRequestImpl;
import com.sportify.register.activity.RegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by fluchey on 2017-04-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {
//    private String username = "";
//    private String password = "JUnitPassword";
//    private String firstName = "JUnitFirstName";
//    private String lastName = "JUnitLastName";
//    private String email = "JUnitMail";
//    private String phoneNumber = "987654321";
    @Mock
    private RegisterView registerView;
    @Mock
    private RegisterRequestImpl registerRequest;
    private RegisterPresenterImpl presenter;


    /**
     * This happens before any other tests.
     * Generates a unique username to be tested against.
     */
    @Before
    public void initObjects() {
        presenter = new RegisterPresenterImpl(registerView);

//        SecureRandom random = new SecureRandom();
//        /* Generates a string which should be kind of unique =)=) */
//        username = new BigInteger(130, random).toString(32);
    }

    /**
     * Add user to database. This should pass.
     */
    @Test
    public void createAccountShouldPass() {
//        SecureRandom random = new SecureRandom();
//        /* Generates a string which should be kind of unique =)=) */
//        username = new BigInteger(130, random).toString(32);
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", username);
//            jsonObject.put("firstname", firstName);
//            jsonObject.put("lastname", lastName);
//            jsonObject.put("mobilnummer", phoneNumber);
//            jsonObject.put("user_mail", email);
//            jsonObject.put("password_user", password);
//            Log.d("JsonObject", jsonObject.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}
