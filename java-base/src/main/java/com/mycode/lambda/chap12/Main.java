package com.mycode.lambda.chap12;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * jf
 * 2018/10/21 15:19
 */
public class Main {
    public static void main(String [] args){
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        //有两种修改方式：1.直接修改为目标值（绝对），2. 在现在的值基础上做修改（相对）
        LocalDate plus = date1.plus(6, ChronoUnit.MONTHS);
        System.out.println(plus);
        LocalDate localDate = date1.plusWeeks(1);
        System.out.println(localDate);
        LocalDate localDate1 = date1.minusDays(3);
        System.out.println(localDate1);
        LocalDate localDate2 = date1.minusYears(3);
        System.out.println(localDate2);
        LocalDate localDate3 = date1.with(ChronoField.DAY_OF_MONTH, 9);
        localDate3 = localDate3.plusYears(6).minusDays(3);
        LocalDate temp = localDate3.withYear(2011);
        //不可变对象，每次修改后都会生成一个新的对象
        System.out.println(temp);
        System.out.println(localDate3);


    }
}
