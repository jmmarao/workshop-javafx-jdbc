package com.jmmarao.workshopjavafxjdbc.utils;

public class ParseUtils {

    public static Integer tryParseStringToInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double tryParseStringToDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
