package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.TestDto;
import com.mountainhome.database.domain.entities.TestEntity;
import com.mountainhome.database.mappers.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;


public class testmapper implements Mapper<TestEntity, TestDto> {

    private ModelMapper modelMapper;


    public testmapper(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;

        Converter<Integer, TestEntity> convertIdToEntity = context -> getbyid(context.getSource());

        PropertyMap<TestDto, TestEntity> mymap = new PropertyMap<>() {
            protected void configure() {
                // Note: this is not normal code. It is "EDSL" so don't get confused
                using(convertIdToEntity).map(source.getInnerId()).setInner(null); // Parameter here is just a placeholder
            }
        };

        this.modelMapper.typeMap(TestEntity.class, TestDto.class).addMapping(
                entity -> entity.getInner().getId(), TestDto::setInnerId
        );
        this.modelMapper.addMappings(mymap);
    }

    @Override
    public TestDto mapTo(TestEntity testEntity) {
        return modelMapper.map(testEntity, TestDto.class);
    }

    @Override
    public TestEntity mapFrom(TestDto testDto) {
        return modelMapper.map(testDto, TestEntity.class);
    }

    public TestEntity getbyid(Integer id) {
        //simulate db call
        if (id == 4) {
            TestEntity ho= TestEntity.builder().id(4).name("inerineriner").build();
            TestEntity ha= TestEntity.builder().id(5).name("inerineriner").build();
            ho.setInner(ha);
            ha.setInner(ho);
            return ho;
        } else return null;
    }
}
