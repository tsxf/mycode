package com.mycode.lambda.chap08;

import java.util.function.Consumer;

/**
 * jf
 * 2018/10/13 18:29
 */
public class OnlineBankingLambda {
    public static void main(String [] args){
          new OnlineBankingLambda().processCustomer(137,(Customer c)-> System.out.println("Hello"));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = DataBase.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    static private class Customer{};
    static private class DataBase{
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
