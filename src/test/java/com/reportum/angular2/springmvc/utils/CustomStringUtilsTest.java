package com.reportum.angular2.springmvc.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomStringUtilsTest {

    @Test
    public void convertTextTest(){
        String actual = CustomStringUtils.convertText("12\n13\n14\n\n45");
        assertThat(actual).isEqualTo("12<br>13<br>14<br><br>45<br>");
    }
}
