package com.surveypedia.tools;

import java.time.LocalDateTime;

public class DateConverter {

    private static StringBuilder builder = new StringBuilder();

    public static String convertDateWithoutTime(LocalDateTime localDateTime) {
        builder.delete(0, builder.toString().length());
        builder.append(localDateTime.getYear()).append("년 ")
        .append(localDateTime.getMonthValue() + "월 ")
        .append(localDateTime.getDayOfMonth() + "일 ");
        return builder.toString();
    }

    public static String convertDateWithTime(LocalDateTime localDateTime) {
        builder.delete(0, builder.toString().length());
        builder.append(localDateTime.getYear()).append("년 ")
                .append(localDateTime.getMonthValue() + "월 ")
                .append(localDateTime.getDayOfMonth() + "일 ")
                .append(localDateTime.getHour() + "시 ")
                .append(localDateTime.getMinute() + "분 ")
                .append(localDateTime.getSecond() + "초");
        return builder.toString();
    }
}
