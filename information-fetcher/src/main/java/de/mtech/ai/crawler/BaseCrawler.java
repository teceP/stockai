package de.mtech.ai.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.mtech.ai.model.FetchedInformation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class BaseCrawler implements Crawler{

    final static Logger logger = LoggerFactory.getLogger(BaseCrawler.class);

    @Autowired
    ObjectMapper objectMapper;

    @Value("${stockai.datastorage.host}")
    private String dataStorageHost;

    @Value("${stockai.datastorage.port}")
    private String dataStoragePort;

    @Override
    public boolean sendToDataStorage(FetchedInformation fetchedInformation) {
        try {
            logger.info(dataStorageHost + ":" + dataStoragePort);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            final String uri = dataStorageHost + ":" + dataStoragePort + "/api/store";
            HttpEntity<String> request = new HttpEntity<String>(objectMapper.writeValueAsString(fetchedInformation), httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri, request, String.class);

            logger.info(result);

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean storeFetchedInformation(FetchedInformation fetchedInformation, String storagePath) {
        final String filename = storagePath + buildFilename(fetchedInformation.getHeadline()).trim();
        File file = new File(filename);

        if (file.exists()) {
            logger.debug("Fetched Information with identical headline already exists.");
            return false;
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath())) {
            IOUtils.write(fetchedInformation.getText(), bufferedWriter);
            logger.trace("File written: " + filename);
            return true;
        } catch (IOException e) {
            logger.warn("File '" + filename + "' could not be written.");
            throw new RuntimeException(e);
        }

    }


    private String buildFilename(String headline) {
        return headline.replace(" ", "_").replace("?", "").replace("!", "").replace(".", "").replace(",", "").replace("'", "").replace("´", "").replace("`", "").replace(":", "").replace("‘","").replace("’","").replace(" ","").trim() + ".txt";
    }
}
