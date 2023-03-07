package de.mtech.ai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class Finals {
    public static DateFormat dateFormatter(){
        DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return dateFormatter;
    }
}
