package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.DateDto;
import com.mountainhome.database.domain.entities.WorldStateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DateMapper {
    @Mapping(target = "day", expression = "java(com.mountainhome.database.util.DateUtil.toDay(source.getDay()))")
    @Mapping(target = "month", expression = "java(com.mountainhome.database.util.DateUtil.toMonth(source.getDay()))")
    @Mapping(target = "season", expression = "java(com.mountainhome.database.util.DateUtil.toSeason(source.getDay()))")
    @Mapping(target = "year", expression = "java(com.mountainhome.database.util.DateUtil.toYear(source.getDay()))")
    DateDto toDateDto(WorldStateEntity source);

    @Mapping(target = "day", expression = "java(com.mountainhome.database.util.DateUtil.toDay(source))")
    @Mapping(target = "month", expression = "java(com.mountainhome.database.util.DateUtil.toMonth(source))")
    @Mapping(target = "season", expression = "java(com.mountainhome.database.util.DateUtil.toSeason(source))")
    @Mapping(target = "year", expression = "java(com.mountainhome.database.util.DateUtil.toYear(source))")
    DateDto toDateDto(Integer source);
}
