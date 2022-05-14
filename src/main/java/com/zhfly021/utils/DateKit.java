package com.zhfly021.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class DateKit {
    public static final DateTimeFormatter GMT_FMT;
    public static final ZoneId GMT_ZONE_ID;
    private static final Map<String, String> PRETTY_TIME_I18N;

    public static int nowUnix() {
        return (int) Instant.now().getEpochSecond();
    }

    public static String toString(long unixTime, String pattern) {
        return Instant.ofEpochSecond(unixTime).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(Date date, String pattern) {
        Instant instant = (new Date(date.getTime())).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDateTime time) {
        return toString(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static int toUnix(String time, String pattern) {
        LocalDateTime formatted = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
        return (int) formatted.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    public static int toUnix(String time) {
        return toUnix(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static int toUnix(Date date) {
        return (int) date.toInstant().getEpochSecond();
    }

    public static Date toDate(String time, String pattern) {
        LocalDate formatted = LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));
        return Date.from(Instant.from(formatted.atStartOfDay(ZoneId.systemDefault())));
    }

    public static Date toDateTime(String time, String pattern) {
        LocalDateTime formatted = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
        return Date.from(formatted.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(String time, String pattern) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime toLocalDateTime(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date toDate(long unixTime) {
        return Date.from(Instant.ofEpochSecond(unixTime));
    }

    public static String gmtDate() {
        return GMT_FMT.format(LocalDateTime.now().atZone(GMT_ZONE_ID));
    }

    public static String gmtDate(LocalDateTime localDateTime) {
        return GMT_FMT.format(localDateTime.atZone(GMT_ZONE_ID));
    }

    public static String gmtDate(Date date) {
        return GMT_FMT.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).atZone(GMT_ZONE_ID));
    }

    public static String prettyTime(LocalDateTime date, Locale locale) {
        if (date == null) {
            return null;
        } else {
            String keyPrefix = locale.getLanguage().equals("zh") ? "zh" : "us";
            long diff = Duration.between(date, LocalDateTime.now()).toMillis() / 1000L;
            int amount;
            if (diff >= 31536000L) {
                amount = (int) (diff / 31536000L);
                return amount == 1 ? (String) PRETTY_TIME_I18N.get(keyPrefix + "_LAST_YEAR") : amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_YEARS");
            } else if (diff >= 2592000L) {
                amount = (int) (diff / 2592000L);
                return amount == 1 ? (String) PRETTY_TIME_I18N.get(keyPrefix + "_LAST_MONTH") : amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_MONTHS");
            } else if (diff >= 604800L) {
                amount = (int) (diff / 604800L);
                return amount == 1 ? (String) PRETTY_TIME_I18N.get(keyPrefix + "_LAST_WEEK") : amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_WEEKS");
            } else if (diff >= 86400L) {
                amount = (int) (diff / 86400L);
                return amount == 1 ? (String) PRETTY_TIME_I18N.get(keyPrefix + "_YESTERDAY") : amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_DAYS");
            } else if (diff >= 3600L) {
                amount = (int) (diff / 3600L);
                return amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_HOURS");
            } else if (diff >= 60L) {
                amount = (int) (diff / 60L);
                return amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_MINUTES");
            } else {
                amount = (int) diff;
                return amount < 6 ? (String) PRETTY_TIME_I18N.get(keyPrefix + "_JUST_NOW") : amount + (String) PRETTY_TIME_I18N.get(keyPrefix + "_SECONDS");
            }
        }
    }

    public static String prettyTime(LocalDateTime date) {
        return prettyTime(date, Locale.getDefault());
    }

    private DateKit() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        GMT_FMT = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        GMT_ZONE_ID = ZoneId.of("GMT");
        PRETTY_TIME_I18N = new HashMap();
        PRETTY_TIME_I18N.put("zh_YEARS", "年前");
        PRETTY_TIME_I18N.put("zh_MONTHS", "个月前");
        PRETTY_TIME_I18N.put("zh_WEEKS", "周前");
        PRETTY_TIME_I18N.put("zh_DAYS", "天前");
        PRETTY_TIME_I18N.put("zh_HOURS", "小时前");
        PRETTY_TIME_I18N.put("zh_MINUTES", "分钟前");
        PRETTY_TIME_I18N.put("zh_SECONDS", "秒前");
        PRETTY_TIME_I18N.put("zh_JUST_NOW", "刚刚");
        PRETTY_TIME_I18N.put("zh_YESTERDAY", "昨天");
        PRETTY_TIME_I18N.put("zh_LAST_WEEK", "上周");
        PRETTY_TIME_I18N.put("zh_LAST_MONTH", "上个月");
        PRETTY_TIME_I18N.put("zh_LAST_YEAR", "去年");
        PRETTY_TIME_I18N.put("us_YEARS", "years ago");
        PRETTY_TIME_I18N.put("us_MONTHS", "months ago");
        PRETTY_TIME_I18N.put("us_DAYS", "days ago");
        PRETTY_TIME_I18N.put("us_HOURS", "hours ago");
        PRETTY_TIME_I18N.put("us_MINUTES", "minutes ago");
        PRETTY_TIME_I18N.put("us_SECONDS", "seconds ago");
        PRETTY_TIME_I18N.put("us_JUST_NOW", "Just now");
        PRETTY_TIME_I18N.put("us_YESTERDAY", "Yesterday");
        PRETTY_TIME_I18N.put("us_LAST_WEEK", "Last week");
        PRETTY_TIME_I18N.put("us_LAST_MONTH", "Last month");
        PRETTY_TIME_I18N.put("us_LAST_YEAR", "Last year");
    }

}
