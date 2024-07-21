package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class test2 {
    public static void main(String args[]) {

        /*List<WorkstationEntity> workstationEntityList = new ArrayList<>();
        WorkstationTypeEntity wt = WorkstationTypeEntity.builder().name("a").build();
        workstationEntityList.add(WorkstationEntity.builder().workstationType(wt).build());
        workstationEntityList.add(WorkstationEntity.builder().workstationType(wt).build());
        Map<String, Integer> a = workstationEntityList.stream().collect(Collectors.toMap(ws -> ws.getWorkstationType().getName(), ws -> 1, Integer::sum));
        System.out.println(a);*/
    }
}
