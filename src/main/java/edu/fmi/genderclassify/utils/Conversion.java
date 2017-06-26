package edu.fmi.genderclassify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Miroslav Kramolinski
 */
public class Conversion {
    public static String getValueAsStr(Object obj) {
        if(obj == null)
            return "";

        if(obj instanceof String)
            return (String) obj;

        if(obj instanceof Double)
            return Double.toString((Double) obj);

        if(obj instanceof Integer)
            return Integer.toString((Integer) obj);

        if(obj instanceof Boolean)
            return Boolean.toString((Boolean) obj);

        if(obj instanceof Long)
            return Long.toString((Long) obj);

        if(obj instanceof Date)
            return new SimpleDateFormat("yyyMMdd").format((Date) obj);

        return null;
    }

    public static double getDoubleValue(Double d) {
        return d == null? 0: d.doubleValue();
    }

    public static double getDoubleValue(Long l) {
        return l == null? 0: l.doubleValue();
    }

    public static double getDoubleValue(Integer i) {
        return i == null? 0: i.doubleValue();
    }
}
