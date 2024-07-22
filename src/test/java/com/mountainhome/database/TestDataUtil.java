package com.mountainhome.database;

import com.mountainhome.database.domain.entities.WorkstationEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    /*public static WorkstationEntity createTestWorkstationTypeByName(String name) {
        return WorkstationEntity.builder().id(1).name(name).build();
    }*/

/*    public static Fortress createTestFortressDefault() {
        return createTestFortressByNameKingId("Gundabad", 5);
    }

    public static Fortress createTestFortressByNameKingId(String name, Integer id) {
        return Fortress.builder().name(name).creation_year(600).age(400).king_id(id).build();
    }

    public static Dwarf createTestDwarfDefault() {
        return createTestDwarfByNamePartnerIdFortressId("Jost", 1, 1);
    }

    public static Dwarf createTestDwarfByNamePartnerIdFortressId(String name, Integer p_id, Integer f_id) {
        return Dwarf.builder().name(name).age((short) 300).height_cm((short) 150).partner_id(p_id).fortress_id(f_id).build();
    }*/
}
