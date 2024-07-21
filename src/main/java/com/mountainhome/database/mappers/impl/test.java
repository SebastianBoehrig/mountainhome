package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.TestDto;
import com.mountainhome.database.domain.entities.TestEntity;
import com.mountainhome.database.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class test {

    public static void main(String args[]) {
        //Create existing mapper
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        //modelMapper.getConfiguration().setPreferNestedProperties(false);
        Mapper<TestEntity, TestDto> mymapper = new testmapper(modelMapper);

        //from entity to dto
        TestEntity inner = TestEntity.builder().id(2).name("testentinner").build();
        TestEntity testEntity = TestEntity.builder().id(1).name("testent").inner(inner).build();
        inner.setInner(testEntity);

        TestDto testDto = mymapper.mapTo(testEntity);

        System.out.println(testDto);


        //from dto to entity
        TestDto testDto2 = TestDto.builder().id(3).name("testdto").innerId(4).build();

        TestEntity testEntity2 = mymapper.mapFrom(testDto2);

        System.out.println(testEntity2.getId());
        System.out.println(testEntity2.getInner().getId());
        System.out.println(testEntity2.getInner().getInner().getId());
        System.out.println(testEntity2.getInner().getInner().getInner().getId());
        System.out.println(testEntity2.getInner().getInner().getInner().getInner().getId());

    }

}
