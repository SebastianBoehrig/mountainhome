package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.DateDto;
import com.mountainhome.database.domain.entities.WorldStateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DateMapper {
    Integer DAYS_PER_MONTH = 12;

    record Month(String name, String season) {
    }

    List<Month> MONTHS = List.of(
            new Month("Shale", "Spring"),
            new Month("Malachite", "Summer"),
            new Month("Hematite", "Summer"),
            new Month("Chert", "Autumn"),
            new Month("Ilmenite", "Winter"),
            new Month("Kimberlite", "Winter"),
            new Month("Stibnite", "Winter"),
            new Month("Sylvite", "Winter")
    );
    Integer DAYS_PER_YEAR = DAYS_PER_MONTH * MONTHS.size();

    @Mapping(target = "day", expression = "java(toDay(source.getDay()))")
    @Mapping(target = "month", expression = "java(toMonth(source.getDay()))")
    @Mapping(target = "season", expression = "java(toSeason(source.getDay()))")
    @Mapping(target = "year", expression = "java(toYear(source.getDay()))")
    DateDto toDateDto(WorldStateEntity source);

    default Integer toDay(Integer day) {
        return day % DAYS_PER_MONTH + 1; // no day 0
    }

    default String toMonth(Integer day) {
        int monthNoYear = day % DAYS_PER_YEAR;
        int monthIndex = monthNoYear / DAYS_PER_MONTH;
        return MONTHS.get(monthIndex).name;
    }

    default String toSeason(Integer day) {
        int monthNoYear = day % DAYS_PER_YEAR;
        int monthIndex = monthNoYear / DAYS_PER_MONTH;
        return MONTHS.get(monthIndex).season;
    }

    default Integer toYear(Integer day) {
        return day / DAYS_PER_YEAR + 1; // no year 0
    }
}
