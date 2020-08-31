package utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeMachine {
    /**
     * This takes a timestamp in UTC and returns a LocalDateTime in the host's local time zone
     *
     * It might make sense to change the output to a String.  That would involve uncommenting two lines below, changing
     * the output type, then reworking code in the Model.  fixme wait till later to see if this makes sense
     * @param inUTC Timestamp in UTC
     * @return LocalDateTime in system default time zone
     */
    public static LocalDateTime utcToLocal(Timestamp inUTC) {
        LocalDateTime stepZero = inUTC.toLocalDateTime();
        ZonedDateTime stepOne = ZonedDateTime.of(stepZero, ZoneId.of("UTC"));
        ZonedDateTime stepTwo = stepOne.withZoneSameInstant(ZoneOffset.systemDefault());
        // DateTimeFormatter outputs a string.  I may have to use this later.
        //DateTimeFormatter customizer = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        LocalDateTime outp = stepTwo.toLocalDateTime();
        //outp = customizer.format(outp);
        return outp;
    }

    // Convert Local To UTC before upload to DB.  todo will implement when needed.

}
