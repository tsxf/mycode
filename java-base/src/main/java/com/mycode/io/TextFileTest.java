package com.mycode.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 蛮小江
 * 2018/8/27 15:43
 */
public class TextFileTest {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 5000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 4000, 1990, 3, 15);
        //保存所有员工信息到文件中
        try (PrintWriter out = new PrintWriter("employee.dat", "UTF-8")) {
            writeData(staff, out);
        }

        //读取刚才的文件信息
            try (Scanner in = new Scanner(new FileInputStream("employee.dat"), "UTF-8")) {
                Employee[] newStaff = readData(in);
                //打印出所有新的读取Employee对象
                for (Employee e : newStaff) {
                    System.out.println(e);
                }
        }

    }

    private static Employee[] readData(Scanner in) {
        //读取出size
        int n = in.nextInt();
        //过滤掉换行符
        in.nextLine();
        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    private static Employee readEmployee(Scanner in) {
        //读取一行
        String line = in.nextLine();
        //分割出|隔开的元素
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        String salary = tokens[1];
        String year = tokens[2];
        String month = tokens[3];
        String day = tokens[4];
        return new Employee(name, Float.parseFloat(salary), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    private static void writeData(Employee[] employees, PrintWriter out) {
        //写入长度
        out.println(employees.length);
        for (Employee e : employees) {
            writeEmployee(e, out);
        }
    }

    private static void writeEmployee(Employee e, PrintWriter out) {
        out.println(e.getName() + "|" + e.getSalary() + "|" + e.getYear() + "|" + e.getMonth() + "|" + e.getDay());
    }
}
