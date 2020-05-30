package ru.javawebinar.topjava.util;

import com.sun.istack.Nullable;
import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class DateTimeFormatter {

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(@Nullable String text, Locale locale) {
            return StringUtils.isEmpty(text) ? null : LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate ld, Locale locale) {
            return ld.format(ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(@org.springframework.lang.Nullable String text, Locale locale) {
            return StringUtils.isEmpty(text) ? null : LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(ISO_LOCAL_TIME);
        }
    }
}
