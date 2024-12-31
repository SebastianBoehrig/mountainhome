package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import jakarta.transaction.Transactional;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class FortressCreationTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FortressRepository fortressRepository;
    @Autowired
    DwarfRepository dwarfRepository;
    private String url;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/fortress";
    }

    @Test
    void createFortressMinimalTest() {
        // When I call the createFortress endpoint with minimal parameters
        FortressDto fortressDto = FortressDto.builder().name("Gundabad").build();
        ResponseEntity<FortressDto> actualReturn = restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then a new fortress is returned with status 201
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertEquals("Gundabad", actualReturn.getBody().getName());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "::Every dwarven fortress has a name!",
            "20:Home:This dwarf doesn't exist!"}, delimiter = ':')
    void createFortressInvalidTest(Integer kingId, String name, String expectedReturn) {
        // When I call the createFortress endpoint with bad parameters
        FortressDto fortressDto = FortressDto.builder().kingId(kingId).name(name).build();
        ResponseEntity<DefaultError> actualReturn = restTemplate.postForEntity(url, fortressDto, DefaultError.class);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertEquals(expectedReturn, actualReturn.getBody().getMessage());
    }

    @Test
    void createFortressIdReturnTest() { //TODO: rework the ids in the data model
        // When I call the createFortress endpoint with parameters that I can't set
        FortressDto fortressDto = FortressDto.builder().creationYear(2020).name("test").build();
        ResponseEntity<FortressDto> actualReturn = restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then the id is getting ignored
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertNotEquals(2020, actualReturn.getBody().getCreationYear());
    }

    @Test
    void createFortressYearTest() {
        // Given the current date is set to 2,2,2

        // When I call the createFortress endpoint
        FortressDto fortressDto = FortressDto.builder().creationYear(2020).name("test").build();
        ResponseEntity<FortressDto> actualReturn = restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then the creationDate of the fortress is set to the current date
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertNotEquals(2020, actualReturn.getBody().getCreationYear());
    }

    @Test
    void createFortressKingExistsTest() {
        // Given a dwarf with id 1 exists
        dwarfRepository.save(DwarfEntity.builder().id(1).build());
        // When I call the createFortress endpoint and try to set the dwarf as king
        FortressDto fortressDto = FortressDto.builder().kingId(1).name("test").build();
        ResponseEntity<FortressDto> actualReturn = restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then the dwarf is made king of that fortress
        assertEquals(HttpStatus.CREATED, actualReturn.getStatusCode());
        assertNotNull(actualReturn.getBody());
        assertEquals(1, actualReturn.getBody().getKingId());
    }

    @Test
    void createFortressKingMigrationTest() {
        // Given a dwarf with id 1 exists in a Fortress with id 1
        fortressRepository.save(FortressEntity.builder().name("Dredge").build());
        dwarfRepository.save(DwarfEntity.builder().id(1).build());
        // When I call the createFortress endpoint and try to set the dwarf as king
        FortressDto fortressDto = FortressDto.builder().kingId(1).name("Fort").build();
        restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then the dwarf is migrated to the new fortress
        Optional<DwarfEntity> dwarf = dwarfRepository.findById(1);
        assertTrue(dwarf.isPresent());
        assertEquals("Fort", dwarf.get().getFortress().getName());
    }

    @Test
    @Transactional
    void createFortressInDBTest() {
        // When I call the createFortress endpoint with a valid fortress
        FortressDto fortressDto = FortressDto.builder().name("Gundabad").build();
        restTemplate.postForEntity(url, fortressDto, FortressDto.class);
        // Then I can find the fortress in the DB
        Optional<FortressEntity> fortressOptional = fortressRepository.findByName("Gundabad");
        assertTrue(fortressOptional.isPresent());
    }
}
