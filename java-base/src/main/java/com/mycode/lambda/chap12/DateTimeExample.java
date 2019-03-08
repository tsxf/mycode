package com.mycode.lambda.chap12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * jf
 * 2018/10/21 16:29
 */
public class DateTimeExample {
    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MM-yyyy");
        }
    };
    public static void main(String [] args){
            //useOldDate();
            //useLocalDate();
            //useTemporalAdjuster();
            useDateFormatter();
    }

    //修改日期，时间
    private static void useTemporalAdjuster() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        LocalDate newYear = date.withYear(2018);
        System.out.println(newYear);

        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);

        date = date.with(lastDayOfMonth());

        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);

        //自定义修改规则
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });

        System.out.println(date);

    }

    private static class NextWorkingDay implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }



    private static void useLocalDate() {
        System.out.println("---------LocalDate----------");
        LocalDate date = LocalDate.of(2014,3, 18);
        int year = date.getYear();//2014
        Month month = date.getMonth();//march
        int day = date.getDayOfMonth();//18
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int length = date.lengthOfMonth();
        boolean leapYear = date.isLeapYear();
        System.out.println(date);

        System.out.println("----------");

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println(y+"-"+m+"-"+d);



        System.out.println("---------LocalTime----------");
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println(time);


        System.out.println("---------LocalDateTime----------");
        LocalDateTime dt1 = LocalDateTime.of(date, time);
        LocalDateTime dt2 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        LocalDateTime dt3 = date.atTime(time);
        LocalDateTime dt4 = date.atTime(13, 45, 20);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt2);

        LocalDate date1 = dt1.toLocalDate();
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(date1);
        System.out.println(time1);

        System.out.println("---------Instant----------");

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        Duration d1 = Duration.between(instant, now);
        Duration d2 = Duration.between(LocalTime.of(13, 45, 10), time);
        System.out.println(d1.getSeconds());//152527928
        System.out.println(d2.getSeconds());//10


        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        System.out.println("---------JapaneseDate----------");
        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);

    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useDateFormatter() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(".")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);

        System.out.println(date.format(complexFormatter));
    }
}
