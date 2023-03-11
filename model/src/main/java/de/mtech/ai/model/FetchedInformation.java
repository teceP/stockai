package de.mtech.ai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fetched_information")
@PrimaryKeyJoinColumn(name = "fetched_information_id")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString(callSuper = true)
public class FetchedInformation extends BaseFetchedInformation {

    @JsonProperty("headline")
    private String headline;

    @ToString.Exclude
    @Lob
    @JsonProperty("text")
    private String text;

    @JsonProperty("time_fetched")
    private long timeFetched;

    /*@Override
    public String toString(){
        Date date = new Date(this.timeFetched);
        final String formattedDate = Finals.dateFormatter().format(date);
        return "Headline: " + headline + " - Text not empty: " + !text.isEmpty() + "  - Time fetched: " + formattedDate;
    }*/

}
