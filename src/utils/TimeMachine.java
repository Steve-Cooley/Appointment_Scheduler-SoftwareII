package utils;

import javafx.util.converter.TimeStringConverter;

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
     * @param timestampInUTC Timestamp in UTC
     * @return LocalDateTime in system default time zone
     */
    public static LocalDateTime utcToLocal(Timestamp timestampInUTC) {
        //algorithm: TS > LDT(UTC) > ZDT(UTC) > ZDT(sysDefault) > LDT(sysDefault)
        LocalDateTime stepZero = timestampInUTC.toLocalDateTime();
        ZonedDateTime stepOne = ZonedDateTime.of(stepZero, ZoneId.of("UTC"));
        ZonedDateTime stepTwo = stepOne.withZoneSameInstant(ZoneOffset.systemDefault());
        // DateTimeFormatter outputs a string.  I may have to use this later.
        //DateTimeFormatter customizer = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        LocalDateTime outp = stepTwo.toLocalDateTime();
        //outp = customizer.format(outp);
        return outp;
    }


    public static Timestamp ldtToTimestamp(LocalDateTime ldt) {
        //algorithm: LDT(sysDefault) > ZDT(sysDefault) > ZDT(UTC) > LDT(UTC) > TS(UTC)
        ZonedDateTime stepOne = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime stepTwo = stepOne.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime stepThree = stepTwo.toLocalDateTime();
        Timestamp outp = Timestamp.valueOf(stepThree);
        return outp;
    }
}
