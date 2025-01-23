package isen.projet_dp_api.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestStrings {

    // START TEST DATA

    public static final String EMAIL_GOOD = "harold@berk.com";

    public static final String PASSWORD = "astrid4life";

    public static final String FIRST_NAME = "Harold";

    public static final String LAST_NAME = "Haddock";

    public static final String COMPANY = "Dragon School";

    // END TEST DATA

    // START TEST STRINGS

    public static final String ERROR_REGISTERING_USER = "Error registering user";

    public static final String ERROR_SEND_EMAIL = "Error sending email";

    // END TEST STRINGS
}
