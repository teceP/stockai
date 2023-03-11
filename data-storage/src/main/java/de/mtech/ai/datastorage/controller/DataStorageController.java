package de.mtech.ai.datastorage.controller;

import de.mtech.ai.datastorage.repository.FetchedInformationRepository;
import de.mtech.ai.model.FetchedInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Offers endpoints for FetchedInformation objects, which will be stored. [where: Filesystem or Database]
 */
@RestController
@RequestMapping("api")
public class DataStorageController {

    @Autowired
    private FetchedInformationRepository fetchedInformationRepository;

    @GetMapping
    @RequestMapping("ping")
    public String ping(){
        return "pong";
    }

    @PostMapping
    @RequestMapping(value = "store", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String storeFetchedInformation(@RequestBody FetchedInformation fetchedInformation){
        final UUID id = fetchedInformationRepository.save(fetchedInformation).getId();

        return "ID:" + id.toString();
    }

}
