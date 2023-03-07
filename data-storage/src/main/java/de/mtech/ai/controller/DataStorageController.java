package de.mtech.ai.controller;

import de.mtech.ai.FetchedInformation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
public class DataStorageController {

    @GetMapping
    @RequestMapping("ping")
    public String ping(){
        return "pong";
    }

    @PostMapping
    @RequestMapping(value = "store", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String storeFetchedInformation(@RequestBody FetchedInformation fetchedInformation){

        return "das hat funktioniert - Received: " + fetchedInformation.getHeadline();
    }

}
