package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.SimpleDwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class FortressGetTest {
    @Autowired
    TestRestTemplate restTemplate;

    private String url;
    @Autowired
    private FortressRepository fortressRepository;
    @Autowired
    private DwarfRepository dwarfRepository;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/fortress";
    }

    @Test
    void getFortressListTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Fort").build());
        // When I call the getFortressList endpoint
        ResponseEntity<String[]> actualResponse = restTemplate.getForEntity(url, String[].class);
        // Then a list with the name of all fortresses is returned
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(1, actualResponse.getBody().length);
        assertEquals("Fort", actualResponse.getBody()[0]);
    }

    @Test
    void getFortressTest() {
        // Given a fortress with a king exists
        DwarfEntity king = DwarfEntity.builder().id(1).build();
        FortressEntity fortress = FortressEntity.builder().name("Fort").creationYear(1).king(king).build();
        fortressRepository.save(fortress);
        // When I call the getFortress endpoint
        ResponseEntity<FortressDto> actualResponse = restTemplate.getForEntity(url + "/{name}", FortressDto.class, "Fort");
        // Then the public facing attributes of the fortress are returned
        FortressDto expectedReturn = FortressDto.builder().name("Fort").kingId(1).creationYear(1).build();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(expectedReturn, actualResponse.getBody());
    }

    @Test
    void getFortressInvalidTest() {
        // When I try to get a non-existing fortress
        ResponseEntity<DefaultError> actualResponse = restTemplate.getForEntity(url + "/{name}", DefaultError.class, "Fort");
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This fortress doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void getDwarfListByFortressTest() {
        // Given dwarves exist in a fortress
        FortressEntity fortress = FortressEntity.builder().name("Fort").build();
        dwarfRepository.save(DwarfEntity.builder().id(1).name("Dwain").fortress(fortress).build());
        dwarfRepository.save(DwarfEntity.builder().id(2).name("Oin").fortress(fortress).build());
        // When I call the getDwarfListByFortress endpoint
        ResponseEntity<SimpleDwarfDto[]> actualResponse = restTemplate.getForEntity(url + "/{name}/dwarves", SimpleDwarfDto[].class, "Fort");
        // Then a list of the dwarves is returned
        SimpleDwarfDto dwarf1 = SimpleDwarfDto.builder().id(1).name("Dwain").build();
        SimpleDwarfDto dwarf2 = SimpleDwarfDto.builder().id(2).name("Oin").build();
        SimpleDwarfDto[] expectedReturn = {dwarf1, dwarf2};

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedReturn);
    }

    @Test
    void getDwarfListByFortressInvalidTest() {
        // When try to get the dwarves of a non-existing fortress
        ResponseEntity<DefaultError> actualResponse = restTemplate.getForEntity(url + "/{name}/dwarves", DefaultError.class, "Fort");
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This fortress doesn't exist!", actualResponse.getBody().getMessage());
    }
}
