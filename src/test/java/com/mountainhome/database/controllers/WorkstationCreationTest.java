package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.WorkstationStoreDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.helper.RestTemplateConfig;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Slf4j
public class WorkstationCreationTest {
    @Autowired
    TestRestTemplate restTemplate;
    private String url;
    @Autowired
    private FortressRepository fortressRepository;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/fortress/{fortress_name}/workstation/{workstation_name}";
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NoFort:Farm:This fortress doesn't exist!",
            "Fort:Perpetuum mobile:This type of Workstation doesn't exist!"}, delimiter = ':')
    void createOrUpdateWorkstationStoreEntityInvalidTest(String fortressName, String workstationTypeName, String expectedReturn) {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Fort").build());
        // When I try to create a new workstation with bad parameters
        ResponseEntity<DefaultError> actualResponse = restTemplate.postForEntity(url, null, DefaultError.class, fortressName, workstationTypeName);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(expectedReturn, actualResponse.getBody().getMessage());
    }

    @Test
    void createOrUpdateWorkstationStoreEntityTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Fort").build());
        // When I create a new workstation in that fortress
        ResponseEntity<WorkstationStoreDto[]> actualResponse = restTemplate.postForEntity(url, null, WorkstationStoreDto[].class, "Fort", "Farm");
        // Then the new workstation-capacity of the fortress is returned
        WorkstationStoreDto[] expectedResponse = {WorkstationStoreDto.builder().workstationTypeName("Farm").amount(1).build()};
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
