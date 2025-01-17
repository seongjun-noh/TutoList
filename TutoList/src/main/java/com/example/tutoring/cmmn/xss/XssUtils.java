package com.example.tutoring.cmmn.xss;

import org.apache.commons.text.StringEscapeUtils;

public class XssUtils {
    public static String charEscape(String value) {
        return value == null ? value : StringEscapeUtils.escapeHtml4(value);
    }
}