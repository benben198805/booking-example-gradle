package rental.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    public static LocalDateTime toLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime toLocalDateTime(String timeString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (null == timeString) {
            return null;
        }
        return LocalDateTime.parse(timeString, df);
    }

    public static Date toDate(String timeString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(timeString);
    }

    public static String toCron(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ss mm HH dd MM ? yyyy");
        return dateFormat.format(localDateTime);
    }

    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return 0;
        }
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
