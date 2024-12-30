package com.mountainhome.database.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultError {
    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
