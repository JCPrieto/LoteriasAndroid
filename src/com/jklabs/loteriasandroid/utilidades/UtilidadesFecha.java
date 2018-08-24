package com.jklabs.loteriasandroid.utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilidadesFecha {

    public static String getHumanReadable(Date time) {
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm");
        return df.format(time);
    }
}
