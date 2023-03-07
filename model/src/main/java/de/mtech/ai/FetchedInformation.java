package de.mtech.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@ToString(callSuper = true)
@Table(name = "fetched_informations")
public class FetchedInformation extends BaseFetchedInformation {

    @JsonProperty("headline")
    private String headline;

    @ToString.Exclude
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
