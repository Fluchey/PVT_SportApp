import com.sportify.register.presenter.RegisterPresenterImpl;
import com.sportify.register.request.RegisterRequestImpl;
import com.sportify.register.activity.RegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        presenter = new RegisterPresenterImpl(registerView);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameISEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showUsernameEmptyError(R.string.username_empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showPasswordEmptyError(R.string.password_empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenFirstNameIsEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("MockPassword");
        when(registerView.getFirstName()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showFirstNameEmptyError(R.string.firstName_Empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenLastNameIsEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("MockPassword");
        when(registerView.getFirstName()).thenReturn("MockFirstName");
        when(registerView.getLastName()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showLastNameEmptyError(R.string.lastName_Empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenPhoneNumberIsEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("MockPassword");
        when(registerView.getFirstName()).thenReturn("MockFirstName");
        when(registerView.getLastName()).thenReturn("MockLastName");
        when(registerView.getPhoneNumber()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showPhoneNumberEmptyError(R.string.phoneNumber_Empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("MockPassword");
        when(registerView.getFirstName()).thenReturn("MockFirstName");
        when(registerView.getLastName()).thenReturn("MockLastName");
        when(registerView.getPhoneNumber()).thenReturn("989238");
        when(registerView.getMail()).thenReturn("");
        presenter.createAccount();

        verify(registerView).showEmailEmptyError(R.string.email_Empty_error);
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsWrongFormat() throws Exception {
        when(registerView.getUserName()).thenReturn("MockTest");
        when(registerView.getPassword()).thenReturn("MockPassword");
        when(registerView.getFirstName()).thenReturn("MockFirstName");
        when(registerView.getLastName()).thenReturn("MockLastName");
        when(registerView.getPhoneNumber()).thenReturn("989238");
        when(registerView.getMail()).thenReturn("asdj");
        presenter.createAccount();

        verify(registerView).showEmailWrongFormatError(R.string.email_wrongFormat_error);
    }
}
