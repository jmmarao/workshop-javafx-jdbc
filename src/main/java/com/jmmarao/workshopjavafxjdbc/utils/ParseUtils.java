package com.jmmarao.workshopjavafxjdbc.utils;

public class ParseUtils {

    public static Integer tryParseStringToInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
