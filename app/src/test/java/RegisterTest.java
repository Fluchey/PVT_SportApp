import com.sportify.register.presenter.RegisterPresenterImpl;
import com.sportify.register.request.RegisterRequestImpl;
import com.sportify.register.activity.RegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sportapp.pvt_sportapp.R;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fluchey on 2017-04-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {
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
//        presenter = new RegisterPresenterImpl(registerView);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(registerView.getPassword()).thenReturn("");
//        when(registerView.getUsername()).thenReturn("Username");
        presenter.createAccount();

        verify(registerView).showPasswordEmptyError(R.string.password_empty_error);
    }


    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        when(registerView.getMail()).thenReturn("");
//        when(registerView.getUsername()).thenReturn("Username");
        when(registerView.getPassword()).thenReturn("MockPassword");

        presenter.createAccount();

        verify(registerView).showEmailEmptyError(R.string.email_Empty_error);
    }

    // TODO Får inte till det här testet. Den klagar på att Pattern är NullPointer. Förmodligen för att det inte ingår i Mock-objektet
    @Test
    public void shouldShowErrorMessageWhenEmailIsWrongFormat() throws Exception {
        when(registerView.getMail()).thenReturn("sddd");
        when(registerView.getPassword()).thenReturn("MockPassword");

        presenter.createAccount();

        verify(registerView).showEmailWrongFormatError(R.string.email_wrongFormat_error);
    }
}
