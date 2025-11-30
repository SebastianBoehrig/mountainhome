package com.mountainhome.database.util;

import java.util.List;

public class DateUtil {
    public static Integer DAYS_PER_MONTH = 12;

    public record Month(String name, String season) {}

    public static List<Month> MONTHS = List.of(
            new Month("Shale", "Spring"),
            new Month("Malachite", "Summer"),
            new Month("Hematite", "Summer"),
            new Month("Chert", "Autumn"),
            new Month("Ilmenite", "Winter"),
            new Month("Kimberlite", "Winter"),
            new Month("Stibnite", "Winter"),
            new Month("Sylvite", "Winter")
    );
    public static Integer DAYS_PER_YEAR = DAYS_PER_MONTH * MONTHS.size();

    public static Integer toDay(Integer day) {
        return day % DAYS_PER_MONTH + 1; // no day 0
    }

    public static String toMonth(Integer day) {
        int monthNoYear = day % DAYS_PER_YEAR;
        int monthIndex = monthNoYear / DAYS_PER_MONTH;
        return MONTHS.get(monthIndex).name;
    }

    public static String toSeason(Integer day) {
        int monthNoYear = day % DAYS_PER_YEAR;
        int monthIndex = monthNoYear / DAYS_PER_MONTH;
        return MONTHS.get(monthIndex).season;
    }

    public static Integer toYear(Integer day) {
        return day / DAYS_PER_YEAR + 1; // no year 0
    }
}
