package isec.airbnbapi.Utils;

import java.util.Date;

public class Utils {
    public static boolean isDateWithinRange(Date start, Date end, Date check) {
        // Check if 'check' is between 'start' and 'end' (inclusive)
        return !check.before(start) && !check.after(end);
    }
}
