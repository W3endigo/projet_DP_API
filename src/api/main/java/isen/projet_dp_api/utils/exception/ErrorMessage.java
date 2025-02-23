package isen.projet_dp_api.utils.exception;

public class ErrorMessage {

    // START DB
    public static final String ERROR_REGISTERING_USER = "Error registering user, ";

    public static final String ERROR_UPDATING_USER = "Error updating user, ";

    public static final String ERROR_USER_ALREADY_EXIST = "user with email [%s] already exists";

    public static final String ERROR_FOREIGN_KEY_NOT_FOUND = "foreign key not found";

    public static final String ERROR_COMPANY_NOT_FOUND = "company with name [%s] not found";

    public static final String ERROR_COMPANY_ALR_EXIST = "company with name [%s] already exist";

    public static final String ERROR_USER_NOT_FOUND = "user with email [%s] not found";


    // END DB

    // START EMAIL

    public static final String ERROR_SEND_EMAIL = "Error sending email, ";

    public static final String ERROR_CONNECT_SMTP = "Couldn't connect to host";

    public static final String ERROR_RENDERING_EMAIL = "Error rendering email";

    // END EMAIL

    // START GENERIC ERRORS

}
