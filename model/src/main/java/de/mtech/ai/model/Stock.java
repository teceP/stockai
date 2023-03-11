package de.mtech.ai.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Stock {
    @Getter
    @Setter
    private String name;

    public String echo() {
        return this.name;
    }
}

