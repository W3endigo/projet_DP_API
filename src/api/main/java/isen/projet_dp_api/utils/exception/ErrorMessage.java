package isen.projet_dp_api.utils.exception;

public class ErrorMessage {

    // START DB
    public static final String ERROR_REGISTERING_USER = "Error registering user, ";

    public static final String ERROR_CREATING_PROJECT = "Error registering a new project, ";

    public static final String ERROR_CREATING_ASSOCIATION_PROJECT_COMPANIES = "Error creating a new association between project and companies, ";

    public static final String ERROR_CREATING_PARTICIPANT = "Error registering a new association between projects and participant, ";

    public static final String ERROR_UPDATING_USER = "Error updating user, ";

    public static final String ERROR_USER_ALREADY_EXIST = "user with email [%s] already exists";

    public static final String ERROR_FOREIGN_KEY_NOT_FOUND = "foreign key not found";

    public static final String ERROR_COMPANY_NOT_FOUND = "company with name [%s] not found";

    public static final String ERROR_UPDATING_PROJECT = "Error updating project, ";

    public static final String ERROR_COMPANY_ALREADY_EXIST = "company with name [%s] already exist";

    public static final String ERROR_PARTCIPANTS_ALREADY_IN_PROJECT = "[%s] already in project";

    public static final String ERROR_COMPANY_ALREADY_IN_PROJECT = "[%s] already in project";

    public static final String ERROR_PROJECT_ALREADY_EXIST = "project with name [%s] already exist";

    public static final String ERROR_ASSOCIATION_NOT_FOUND = "Association not found";

    public static final String ERROR_PARTICIPANT_NOT_FOUND = "This participant does not exist";

    public static final String ERROR_DELETING_USER = "This user does not exist";

    public static final String ERROR_USER_HAS_PROJECTS = "This user is the chef of one or more projects and can't be deleted";

    public static final String ERROR_USER_NOT_FOUND = "user with email [%s] not found";


    // END DB

    // START EMAIL

    public static final String ERROR_SEND_EMAIL = "Error sending email, ";

    public static final String ERROR_CONNECT_SMTP = "Couldn't connect to host";

    public static final String ERROR_RENDERING_EMAIL = "Error rendering email";

    // END EMAIL

    // START GENERIC ERRORS

    public static final String ERROR_INTERNAL_SERVER = "Internal server error";



}
