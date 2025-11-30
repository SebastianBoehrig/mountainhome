package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.mountainhome.database.mappers.DateMapper.MONTHS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Slf4j
public class DateGetTest {
    @Autowired
    TestRestTemplate restTemplate;
    private String url;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/date";
    }

    @Test
    void getDateTest() {
        // When I get the day of the app
        ResponseEntity<DateDto> actualReturn = restTemplate.getForEntity(url, DateDto.class);
        // Then the day 0 is returned with status 200
        DateDto expectedReturn = DateDto.builder()
                .day(1)
                .month(MONTHS.getFirst().name())
                .season(MONTHS.getFirst().season())
                .year(1).build();
        assertEquals(OK, actualReturn.getStatusCode());
        DateDto actualDate = actualReturn.getBody();
        assertNotNull(actualDate);
        assertEquals(expectedReturn, actualDate);
    }
}
