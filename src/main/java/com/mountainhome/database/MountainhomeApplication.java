package com.mountainhome.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MountainhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountainhomeApplication.class, args);
    }
    // TODO look at all the startup warning msg!
    // TODO remove exesively large mariadb logfiles!
}
