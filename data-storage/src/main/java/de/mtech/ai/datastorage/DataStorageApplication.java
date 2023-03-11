package de.mtech.ai.datastorage;

import de.mtech.ai.model.FetchedInformation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The DataStorage module receives and persists data, e.g. FetchedInformation objects.
 * It also provides an endpoint to provide FetchedInformation for e.g. the "Brain" module.
 * This module is the only module which contains connection to a database.
 */
@SpringBootApplication
@EntityScan("de.mtech.ai.model")
public class DataStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataStorageApplication.class, args);
    }
}