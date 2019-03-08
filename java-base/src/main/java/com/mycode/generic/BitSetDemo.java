package com.mycode.generic;

import java.util.BitSet;

/**
 * 蛮小江
 * 2018/8/15 10:02
 */
public class BitSetDemo {
    public static void main(String[] args) {
        //testBasicUse();
        int n = 2000000;
        int count =0 ;
        long start = System.currentTimeMillis();
        BitSet b = new BitSet(n + 1);
        int i ;
        //所有2-2000000元素位置设置true
        for (i = 2;i<=n;i++) {
            b.set(i);
        }
        //清楚掉，素数的倍数所对应位置都设置为关状态
         i = 2;
        while(i*i<= n){
            if(b.get(i)){
                //满足素数的条件
                count++;
                int k = 2 *i;
                while (k <= n) {
                    b.clear(k);
                    k+=i;
                }
            }
            i++;
        }
        System.out.println("count:"+count);
        //满足条件，返回true都是素数
        while (i <= n) {
               if(b.get(i)){
                    count++;
               }
               i++;
        }

        long end = System.currentTimeMillis();
        System.out.println(count+" primes");
        System.out.println((end-start)+" milliseconds");
    }

    private static void testBasicUse() {
        BitSet bitSet = new BitSet(16);
        bitSet.set(0);
        bitSet.clear(0);
        int length = bitSet.length();
        System.out.println("length:"+length);
        for (int i= 0 ;i <length;i++){
            System.out.println(bitSet.get(i));
        }
    }
}
