package com.example.apphotel.AdditionalProfile.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static  final  String DATE_FORMAT="yyyy-MM-dd";
    public static Date convertFormStringToDate(String dateString) throws ParseException {
        DateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.parse(dateString);
    }


}
