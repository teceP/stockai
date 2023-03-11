package de.mtech.ai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "base_fetched_information")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Data
public abstract class BaseFetchedInformation {

    @Id
    @GeneratedValue
    @Column(name = "fetched_information_id")
    private UUID id;

}
