package ar.edu.itba.ingesoft.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,15}$";
    private static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    /**
     * Validates the password strength:
     *      More than 8 characters
     *      At least one digit
     *      At least one uppercase
     *      At least one lowercase
     *      At least one special
     * @param pass to be validated.
     * @return true if valid, false if invalid.
     */
    public static boolean ValidatePassword(final String pass){
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(pass);
        return (m.matches());
    }

    /**
     * Validates the email format.
     * @param email to be validated.
     * @return true if valid, false if invalid.
     */
    public static boolean ValidateEmail(final String email){
        return (Pattern.matches(EMAIL_PATTERN, email));
    }
}
