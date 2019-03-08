package com.mycode.lambda.chap08;

import java.util.ArrayList;
import java.util.List;

/**
 * jf
 * 2018/10/13 18:07
 */
public class ObserverMain {


    public static void main(String[] args) {
        //具体的主题
        Feed f = new Feed();
        //注册观察者
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObserver("The queen said her favourite book is Java8 In Action");

        System.out.println("------------");

        //用lambda
        Feed feedLambda = new Feed();
        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("queue")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        feedLambda.notifyObserver("Money money money, give me money!");
    }

    //观察者
    @FunctionalInterface
    interface Observer {
        void inform(String tweet);
    }

    //主题
    interface Subject {
        void registerObserver(Observer observer);

        void notifyObserver(String tweet);
    }

    static private class NYTimes implements Observer {
        @Override
        public void inform(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY!" + tweet);
            }
        }
    }

    static private class Guardian implements Observer {
        @Override
        public void inform(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London ....." + tweet);
            }
        }
    }

    static private class LeMonde implements Observer {
        @Override
        public void inform(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese,wine and news !" + tweet);
            }
        }
    }


    static private class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer observer) {
            this.observers.add(observer);

        }

        @Override
        public void notifyObserver(String tweet) {
            this.observers.forEach(o -> o.inform(tweet));
        }
    }


}
