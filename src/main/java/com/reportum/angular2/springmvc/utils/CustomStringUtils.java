package com.reportum.angular2.springmvc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class CustomStringUtils {

    private CustomStringUtils(){}

    private static final String BREAK = "<br>";

    public static String convertText(String text){
        return replaceEndOfLineByBreak(text);
    }

    private static String replaceEndOfLineByBreak(String text){
        StringReader stringReader = new StringReader(text);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line).append(BREAK);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
