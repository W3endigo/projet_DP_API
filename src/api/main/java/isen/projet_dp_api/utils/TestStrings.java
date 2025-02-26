package isen.projet_dp_api.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@Profile("test")
public class TestStrings {

    // START TEST DATA

    public static final String EMAIL_HAROLD = "harold@berk.com";

    public static final String EMAIL_ASTRID = "astrid@berk.com";

    public static final String EMAIL_KROKMOU = "krokmou@berk.com";

    public static final String PASSWORD = "astrid4life";

    public static final String FIRST_NAME = "Harold";

    public static final String LAST_NAME = "Haddock";

    public static final String COMPANY = "Dragon School";

    public static final String FIRST_NAME_SECONDARY = "Astrid";

    public static final String LAST_NAME_SECONDARY = "Hofferson";

    public static final String PASSWORD_SECONDARY = "hiccup4life";

    public static final String COMPANY_SECONDARY = "Chief of Berk";

    public static final String COMPANY_THIRD = "Apple";

    public static final String DESCRIPTION = "Project description";

    public static final String TITLE = "Project";

    public static final Integer PROJECT_COMP_ID = 1;

    public static final Date START_DATE;

    public static final Date END_DATE;


    static {
        try {
            java.util.Date utilStartDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02");
            java.util.Date utilEndDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02");

            START_DATE = new Date(utilStartDate.getTime());
            END_DATE = new Date(utilEndDate.getTime());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    // END TEST DATA

    // START TEST STRINGS

    // END TEST STRINGS
}
