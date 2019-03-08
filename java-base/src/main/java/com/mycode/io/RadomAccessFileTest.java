package com.mycode.io;

import java.io.*;

/**
 * 蛮小江
 * 2018/9/13 11:24
 */
public class RadomAccessFileTest {
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("哈利 Hacker", 5000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 4000, 1990, 3, 15);

        //写入数据
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat"))) {
            for (Employee e : staff) {
                     writeData(out,e);
            }
        }

        //读数据
        try(RandomAccessFile in = new RandomAccessFile("employee.dat","r")) {

            //先计算有多少行数据
            long length = in.length();
            System.out.println("length:"+length);

            int row = (int) (length / Employee.RECORD_SIZE);

            //还原数据
            Employee[] newStaff = new Employee[row];

            for (int i = row - 1; i >= 0; i--) {
                newStaff[i] = new Employee();
                //定位
                in.seek(i * Employee.RECORD_SIZE);
                newStaff[i]= readData(in);
            }

            //打印出，还原的对象
            for (Employee e : newStaff) {
                System.out.println(e);
            }

        }

    }

    private static Employee readData(RandomAccessFile in) throws IOException {
        String name = DataIO.readFixedString(Employee.NAME_SIZE, in);
        double salary = in.readDouble();
        int year = in.readInt();
        int month = in.readInt();
        int day = in.readInt();
        return new Employee(name,salary,year,month,day);
    }

    private static void writeData(DataOutputStream out, Employee e) throws IOException {
        //先写入固定的字符串长度
        //名字   长度：40 * 2
        DataIO.writeFixedString(e.getName(),Employee.NAME_SIZE,out);
        //写入薪水 长度：1 * 8
        out.writeDouble(e.getSalary());
        //写入日期 长度：3 * 4
        out.writeInt(e.getYear());
        out.writeInt(e.getMonth());
        out.writeInt(e.getDay());
    }
}
