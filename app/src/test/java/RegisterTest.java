import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by fluchey on 2017-04-15.
 */

public class RegisterTest {
    /**
     * Create unique username and add to database.
     * Should return false when we try to add the same username a second time.
     */
    @Test
    public void createAccount(){
        SecureRandom random = new SecureRandom();

        /* Generates a string which should be kind of unique =)=) */
        String username = new BigInteger(130, random).toString(32);

    }
}
