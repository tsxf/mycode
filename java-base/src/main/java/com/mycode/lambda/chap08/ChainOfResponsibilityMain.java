package com.mycode.lambda.chap08;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * jf
 * 2018/10/13 12:33
 */
public class ChainOfResponsibilityMain {
    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);
        System.out.println("------");


        //UnaryOperator<String> headerProcessing;
        Function<String,String> headerProcessing = (String text)->"From Raoul, Mario and Alan: " + text;
        Function<String,String> spellCheckerProcessing =(String text)-> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result1 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result1);
    }

    static private abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        protected abstract T handleWork(T input);
    }

    static private class HeaderTextProcessing extends  ProcessingObject<String>{
        @Override
        protected String handleWork(String text) {
             return "From Raoul, Mario and Alan: " + text;
        }
    }

    static private class SpellCheckerProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }



}
