package General;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data_Checks {
	//an future class for checking the data that comes , for the QA admin (yam)
    // Define a regular expression pattern for validating email addresses
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
