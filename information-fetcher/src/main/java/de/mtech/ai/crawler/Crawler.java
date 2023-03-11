package de.mtech.ai.crawler;

import de.mtech.ai.model.FetchedInformation;

import java.util.List;

public interface Crawler {

    String STORAGE_DIRECTORY = System.getProperty("user.dir") + "/fetched-informations/";

    String MARKETWATCH = "marketwatch";

    List<FetchedInformation> fetchInformation();

    /**
     * Stores fetched information pojo to harddrive.
     * Will be implemented by datastorage module.
     * @param fetchedInformation
     * @param storagePath
     * @return
     */
    boolean storeFetchedInformation(FetchedInformation fetchedInformation, String storagePath);

    boolean sendToDataStorage(FetchedInformation fetchedInformation);
}
