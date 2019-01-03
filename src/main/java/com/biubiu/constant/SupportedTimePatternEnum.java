package com.biubiu.constant;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Haibiao.Zhang on 2019/1/3.
 */
public enum SupportedTimePatternEnum {

    ONE("yyyy-MM-dd");

    SupportedTimePatternEnum(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    /**
     * 判断是否是日期并且解析日期
     */
    public static Date parseDate(String time) {
        Date date = null;
        for (SupportedTimePatternEnum value : SupportedTimePatternEnum.values()) {
            try {
                if (date != null) break;
                date = doParseDate(time, value.pattern);
            } catch (Exception e) {
                //doNothing
            }
        }
        return date;
    }

    private static Date doParseDate(String time, String pattern) {
        if (StringUtils.isEmpty(time)) return null;
        return Date.from(LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern)).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
