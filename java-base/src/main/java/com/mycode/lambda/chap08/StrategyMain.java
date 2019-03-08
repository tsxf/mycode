package com.mycode.lambda.chap08;

/**
 * jf
 * 2018/10/13 11:01
 */
public class StrategyMain {
    public static void main(String [] args){
             //使用匿名、声明行为
        Validator v1 = new Validator(new IsAllLowerCase());
        System.out.println(v1.validate("aaaa"));

        Validator v2 = new Validator(new IsNumeric());
        System.out.println(v2.validate("vvvv"));

        //lambda
        System.out.println("------------------");

        Validator v3 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v3.validate("aaaaa"));

        Validator v4 = new Validator(s -> s.matches("\\d+"));
        System.out.println(v4.validate("bbbbbb"));
    }
    interface ValidationStrategy{
        boolean execute(String s);
    }

    static private class IsAllLowerCase implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    static private class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }


      private static class  Validator{
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return  strategy.execute(s);
        }
    }

    interface A{
        Object  o = new Object();
    }
}
