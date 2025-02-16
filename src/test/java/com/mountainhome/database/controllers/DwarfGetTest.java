package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.repositories.DwarfRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class DwarfGetTest {
    @Autowired
    TestRestTemplate restTemplate;

    private String url;
    @Autowired
    private DwarfRepository dwarfRepository;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/dwarf/{dwarf_id}";
    }

    @Test
    void getDwarfTest() {
        // Given a dwarf with maximum attributes exists
        FortressEntity fortress = FortressEntity.builder().name("Fort").build();
        DwarfEntity partner = DwarfEntity.builder().id(1).name("Theresa").fortress(fortress).build();
        dwarfRepository.save(DwarfEntity.builder().id(1).name("Dwain").fortress(fortress).partner(partner).heightInCm((short) 100).build());
        // When I call the getDwarf endpoint
        ResponseEntity<DwarfDto> actualResponse = restTemplate.getForEntity(url, DwarfDto.class, 2);
        // Then the dwarf is returned with all public facing attributes
        DwarfDto expectedResponse = DwarfDto.builder().id(2).partnerId(1).name("Dwain").fortress("Fort").heightInCm((short) 100).workstationSkill(new HashMap<>()).build();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(expectedResponse, actualResponse.getBody());
    } //TODO: test for workstationskill

    @Test
    void getDwarfInvalidTest() {
        // When I try to get a non-existing dwarf
        ResponseEntity<DefaultError> actualResponse = restTemplate.getForEntity(url, DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This dwarf doesn't exist!", actualResponse.getBody().getMessage());
    }
}
