package com.mycode.lambda.chap08;

/**
 * jf
 * 2018/10/13 18:25
 */
abstract class OnlineBanking {
    public static void main(String[] args) {

    }

    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCutomerHappy(c);
    }

    abstract void makeCutomerHappy(Customer c) ;

    static private class Customer {
    }

    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
