package de.mtech.ai.datastorage.repository;

import de.mtech.ai.model.FetchedInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FetchedInformationRepository extends JpaRepository<FetchedInformation, UUID> {

}

