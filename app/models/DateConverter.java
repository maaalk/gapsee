package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String showDate(Date date){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
