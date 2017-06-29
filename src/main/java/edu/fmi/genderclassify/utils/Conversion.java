package edu.fmi.genderclassify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public static String getHexColorAsNominal(String hexColor) {
        String pattern = "[\\w|\\d]+";
        if (!hexColor.matches(pattern)) {
            return "FFFFFF";
        }

        int borderOne = 85;
        int borderTwo = 170;
        int borderThree = 255;

        int color = (int)Long.parseLong(hexColor, 16);
        int R = (color >> 16) & 0xFF;
        int G = (color >> 8) & 0xFF;
        int B = (color >> 0) & 0xFF;

        int nominalR = 0;
        if (R <= borderOne) nominalR = borderOne;
        else if (R > borderOne && R <= borderTwo) nominalR = borderTwo;
        else if (R > borderTwo && R <= borderThree) nominalR = borderThree;

        int nominalG = 0;
        if (G <= borderOne) nominalG = borderOne;
        else if (G > borderOne && G <= borderTwo) nominalG = borderTwo;
        else if (G > borderTwo && G <= borderThree) nominalG = borderThree;

        int nominalB = 0;
        if (B <= borderOne) nominalB = borderOne;
        else if (B > borderOne && B < borderTwo) nominalB = borderTwo;
        else if (B > borderTwo && B <= borderThree) nominalB = borderThree;

        String result = Integer.toHexString( nominalR ) +
                Integer.toHexString( nominalG ) +
                Integer.toHexString( nominalB );

        return result;
    }

    public static Double getMinMaxNormalizedValue(Double value, List<Double> allValues) {
        if(value == null || value.isNaN() || allValues == null || allValues.isEmpty())
            return null;

        return (value - allValues.stream().min(Double::compareTo).get())
                / (allValues.stream().max(Double::compareTo).get() - allValues.stream().min(Double::compareTo).get());
    }
}
