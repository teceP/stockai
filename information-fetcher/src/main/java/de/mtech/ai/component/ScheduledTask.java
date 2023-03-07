package de.mtech.ai.component;

import de.mtech.ai.FetchedInformation;
import de.mtech.ai.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static de.mtech.ai.crawler.Crawler.MARKETWATCH;
import static de.mtech.ai.crawler.Crawler.STORAGE_DIRECTORY;


@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    @Qualifier(MARKETWATCH)
    private Crawler marketwatchCrawler;

    @Scheduled(fixedRate = 50000)
    public void reportCurrentTime() {
        logger.info("The time is now {}", dataFormat.format(new Date()));

        File storageDir = new File(STORAGE_DIRECTORY);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        List<FetchedInformation> fetchedInformationList = marketwatchCrawler.fetchInformation();
        fetchedInformationList.forEach(fetchedInformation -> {
            marketwatchCrawler.sendToDataStorage(fetchedInformation);
            //marketwatchCrawler.storeFetchedInformation(fetchedInformation, STORAGE_DIRECTORY);
        });
    }

}
