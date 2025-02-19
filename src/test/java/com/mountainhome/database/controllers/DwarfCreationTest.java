package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.dto.ResourceDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.repositories.FortressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Slf4j
class DwarfCreationTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FortressRepository fortressRepository;
    private String url;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/dwarf";
    }

    @Test
    void createDwarfMinimalTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Mons").build());
        // When I create a dwarf at this fortress
        DwarfDto dwarfDto = DwarfDto.builder().name("Gloin").fortress("Mons").build();
        ResponseEntity<DwarfDto> actualReturn = restTemplate.postForEntity(url, dwarfDto, DwarfDto.class);
        // Then a new dwarf is returned with status 200
        DwarfDto expectedReturn = DwarfDto.builder().name("Gloin").fortress("Mons")
                .id(1).birthday(LocalDate.of(0, 1, 1))
                .workstationSkill(Map.of("Farm", 0)).build();
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        DwarfDto actualDwarf = actualReturn.getBody();
        assertNotNull(actualDwarf);
        actualDwarf.setHeightInCm(null);
        assertEquals(expectedReturn, actualDwarf);
    }

    @Test
    void createDwarfIgnoresTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Mons").build());
        // When I try to create a dwarf with parameters that I can't set
        ResourceDto favFood = ResourceDto.builder().id(12).name("NotFruit").build();
        Map<String, Integer> workstationSkill = new HashMap<>();
        workstationSkill.put("NotThere", 12);
        DwarfDto dwarfDto = DwarfDto.builder().name("Dain").fortress("Mons")
                .birthday(LocalDate.of(12, 12, 12))
                .workstationSkill(workstationSkill)
                .partnerId(12).favoriteFood(favFood).build();
        ResponseEntity<DwarfDto> actualReturn = restTemplate.postForEntity(url, dwarfDto, DwarfDto.class);
        // Then the parameters get ignored
        DwarfDto expectedReturn = DwarfDto.builder().name("Dain").fortress("Mons")
                .id(1).birthday(LocalDate.of(0, 1, 1))
                .workstationSkill(Map.of("Farm", 0)).build();
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        DwarfDto actualDwarf = actualReturn.getBody();
        assertNotNull(actualDwarf);
        actualDwarf.setHeightInCm(null);
        assertEquals(expectedReturn, actualDwarf);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Nain::Every dwarf has a fortress!",
            ":MyFortress:Every dwarf has a name!",
            "Nain:MyFortress:This fortress doesn't exist!"}, delimiter = ':')
    void createDwarfInvalidTest(String name, String fortressName, String expectedReturn) {
        // When I create a dwarf with bad parameters
        DwarfDto dwarfDto = DwarfDto.builder().name(name).fortress(fortressName).build();
        ResponseEntity<DefaultError> actualReturn = restTemplate.postForEntity(url, dwarfDto, DefaultError.class);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertEquals(expectedReturn, actualReturn.getBody().getMessage());
    }

    @Test
    void createDwarfHeightTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Mons").build());
        // When I create dwarves in that fortress
        DwarfDto dwarfDto = DwarfDto.builder().name("Oin").fortress("Mons").build();
        ResponseEntity<DwarfDto> actualReturn = restTemplate.postForEntity(url, dwarfDto, DwarfDto.class);
        ResponseEntity<DwarfDto> actualReturn2 = restTemplate.postForEntity(url, dwarfDto, DwarfDto.class);
        // Then the height gets randomly assigned each time
        assertNotNull(actualReturn.getBody());
        assertTrue(actualReturn.getBody().getHeightInCm() >= 120);
        assertTrue(actualReturn.getBody().getHeightInCm() <= 180);

        assertNotNull(actualReturn2.getBody());
        assertTrue(actualReturn2.getBody().getHeightInCm() >= 120);
        assertTrue(actualReturn2.getBody().getHeightInCm() <= 180);

        assertNotEquals(actualReturn.getBody().getHeightInCm(), actualReturn2.getBody().getHeightInCm());
    }

    @Test
    void createDwarfHeightIgnoreTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Mons").build());
        // When I try to create a dwarf with height parameter in that fortress
        DwarfDto dwarfDto = DwarfDto.builder().name("Oin").heightInCm((short) 20).fortress("Mons").build();
        ResponseEntity<DwarfDto> actualReturn = restTemplate.postForEntity(url, dwarfDto, DwarfDto.class);
        // Then the height parameter gets ignored
        assertNotNull(actualReturn.getBody());
        assertNotEquals((short) 20, actualReturn.getBody().getHeightInCm());
    }
}
