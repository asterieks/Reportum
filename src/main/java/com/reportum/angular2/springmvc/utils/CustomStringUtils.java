package com.reportum.angular2.springmvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class CustomStringUtils {

    private CustomStringUtils(){}

    private static final String BREAK = "<br>";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomStringUtils.class);

    public static String convertText(String text){
        return text !=null ? replaceEndOfLineByBreak(text):"";
    }

    private static String replaceEndOfLineByBreak(String text){
        StringReader stringReader = new StringReader(text);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line).append(BREAK);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return text;
    }
}
