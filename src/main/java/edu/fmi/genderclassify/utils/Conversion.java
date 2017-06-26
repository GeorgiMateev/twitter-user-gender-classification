package edu.fmi.genderclassify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Miroslav Kramolinski
 */
public class Conversion {
    public static String getValueAsStr(Object obj) {
        if(obj == null)
            return null;

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
}
